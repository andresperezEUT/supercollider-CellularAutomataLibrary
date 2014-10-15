// Andrés Pérez, 2011
// www.andresperezlopez.com

//see helpfiles for complete description of rule types

Margolus2d : DiscreteType2d {

	var <ruleSize;

	*new{ 
		^super.new.init;
	}
	
	init {
		super.init(2,1,\margolus);
		m=4;	// current cell is counted also like neighbor
		ruleSize=k**m; // (=16)
		neighborhood=neighborhood.rotate;

		
	}
	
	setRule { |r|
		rule=Dictionary.new(ruleSize);
		ruleSize.do {|i| rule.put(i.asBinaryDigits(m),r[i].asBinaryDigits(m))}
	}
	
	getRule{
		^(0..ruleSize-1).collect {|i| rule.at(i.asBinaryDigits(m)).base2decimal};
	}

	getNextMatrix { |matrix,border,count|
		var cell,neighbors;
		var newMatrix=Array2D.new(matrix.rows,matrix.cols);		
		var values;
		neighborhood=neighborhood.neg;
		// periodic borders
		if (border.isNil){
			forBy(0,matrix.cols-1,2,{|col|
				forBy(0,matrix.rows-1,2,{|row|
					cell=matrix.at(row,col);					
					neighbors=neighborhood.collect { |pos| matrix.periodicAt(row+pos[1],col+pos[0])	};					
					values=this.applyRule(cell,neighbors,count);					
					if (count.even) {
						neighborhood.do {|pos,i| newMatrix.periodicPut(row+pos[1],col+pos[0],values[3-i])};
						newMatrix.put(row,col,values[0]);
					} {
						neighborhood.do {|pos,i| newMatrix.periodicPut(row+pos[1],col+pos[0],values[i])};
						newMatrix.put(row,col,values[3]);
					}
				})
			})
		
		} {  
		// border 
			if (count.even) {
				forBy(0,matrix.cols,2,{|col|
					forBy(0,matrix.rows,2,{|row|				
						cell=matrix.borderAt(row,col,border);						
						neighbors=neighborhood.collect { |pos| matrix.borderAt(row+pos[1],col+pos[0],border)};
						values=this.applyRule(cell,neighbors,count);
						neighborhood.do {|pos,i| newMatrix.borderPut(row+pos[1],col+pos[0],values[3-i],border)};
						newMatrix.borderPut(row,col,values[0],border);
				})
			})
			} { 
			// odd
				forBy(0,matrix.cols-1,2,{|col|
					forBy(0,matrix.rows-1,2,{|row|
						cell=matrix.borderAt(row,col);
						neighbors=neighborhood.collect { |pos| matrix.borderAt(row+pos[1],col+pos[0],border)	};
						values=this.applyRule(cell,neighbors,count);
						neighborhood.do {|pos,i| newMatrix.borderPut(row+pos[1],col+pos[0],values[i])};
						newMatrix.put(row,col,values[3]);
					})
				})
			}
		};
		^newMatrix;
	}

	applyRule {|cell,neighbors,count|
		if (count.even)
			{^rule.at((neighbors++cell).reverse)}
			{^rule.at(neighbors++cell)}
	}
	

	
	getIndices { |i,matrix|
		matrix.rows.do {|row|
			matrix.cols.do {|col|
				if ( (row+i).even and: {(col+i).even}) {matrix.put(row,col,true)} {matrix.put(row,col,false)}
			}		
		};
		^matrix;
	}
	
	printOn { arg stream;
			stream << "Two-Dimensional Margolus Rule with rule=" << this.getRule << ", neighborhood=" << neighborhoodType;
	}

}
