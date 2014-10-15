// Andrés Pérez, 2011
// www.andresperezlopez.com

//abstract class
//describes standard behaviour/methods for all rule types
//each rule type must implement its own behaviour

//see helpfiles for complete description of rule types

RuleType2d {

	var <k;
	var <r,<m;
	var <neighborhoodType;
	var <neighborhood;
	var <type;
	var customMatrix;
	var <>name;
	

	getNeighborhood {
		neighborhood=List.new;
		(0-r..r).do {|x|
			(0-r..r).do {|y|
				if (this.pointBelongsToNeighborhood(x,y)) {neighborhood.add([x,y])};
			}
		};
		neighborhood.removeAllSuchThat{ |item| item==[0,0]};
		m=neighborhood.size;
	}
	
	//posiciones con x creciente a la derecha, y creciente abajo (como en las matrices)!!!!
	pointBelongsToNeighborhood { |x,y|
				switch (neighborhoodType)
					{\moore} 	  {^true}
					{\vonNeumann} { ^( abs(x)+abs(y)) <= r }
					{\cross}	  { ^( (abs(x)+abs(y)) <= r and: { abs(x) != abs(y) })}
					{\diagonal}   {^( abs(x) == abs(y) )}					
					{\chess}	  {^( mod( abs(x)+abs(y) , 2 ) == 0 )}
					{\margolus}   {^( (x >= 0) and: { y >= 0 } )}
					{\custom}	  {^(customMatrix.at(y+r,x+r)==1)}
					{"Incorrect types".warn};
	}
	
	getNextMatrix { |matrix,border|
		var cell,neighbors;
		var newMatrix=Array2D.new(matrix.rows,matrix.cols);
				
		matrix.rows.do {|row|
			matrix.cols.do { |col|								
				cell=matrix.at(row,col);					
				neighbors=neighborhood.collect { |pos| 
					if (border.isNil)
						//periodic borders
						{matrix.periodicAt(row+pos[1],col+pos[0])}
						//border to integer
						{matrix.borderAt(row+pos[1],col+pos[0],border)}
					};
				newMatrix.put(row,col,this.applyRule(cell,neighbors));
			}
		};
		^newMatrix;
	}
	
	applyRule {}
	
	setNeighborhood{
		var m=(2*r)+1;
		var cellSize=20;
		var matrix=Array2D.fromArray(m,m,0.dup(m*m));
		var value;
		var w = Window("custom Neighborhood", Rect(100, 500, cellSize*m, cellSize*m+100)).userCanClose_(false).front;
		var b=Button.new(w,Rect(0,cellSize*m,30,30)).states_([["OK",Color.white,Color.black]]).action_({
			w.close;
			customMatrix=matrix;
			this.getNeighborhood;
		});
		var u= UserView(w,Rect(0,0,cellSize*m,cellSize*m)).background_(Color.black).drawFunc= {
			m.do{|y|				
				m.do{|x|		
					value=matrix.at(y,x);			
					if(value>0) {
						Pen.fillColor= Color.red;
						Pen.addRect(Rect(x*cellSize, y*cellSize, cellSize,cellSize));
						Pen.fill;
					};
				};
			};	
			Pen.fillColor=Color.white;
			Pen.addRect(Rect(r*cellSize,r*cellSize,cellSize,cellSize));
			Pen.fill;
			
			Pen.strokeColor=Color.green;
			forBy(0,cellSize*m,cellSize,{|x|
				Pen.moveTo(x@0); Pen.lineTo(x@(cellSize*m));
				Pen.moveTo(0@x); Pen.lineTo((cellSize*m)@x);
			});
			Pen.stroke;
		};
		u.mouseDownAction_({ |v,x,y|
			var posX=(x/cellSize).floor;
			var posY=(y/cellSize).floor;
			var value=matrix.at(posY,posX);
			matrix.put(posY,posX,mod((value+1),2));
			u.refresh;
		});
		}
		
		isDiscrete {
			if (this.class.superclasses.includes(DiscreteType2d)) {^true} {^false};
		}
		isContinuous {
			if (this.class.superclasses.includes(ContinuousType2d)) {^true} {^false};
		}
}

DiscreteType2d : RuleType2d {

	
	var <rule;
	
	init { |states,ratio,nT|
		k=states;
		r=ratio;
		type=this.class.name;
		neighborhoodType=nT;
		if (nT==\custom) 
			{this.setNeighborhood}
			{this.getNeighborhood};		
	}
}

ContinuousType2d : RuleType2d {

	var <a,<b;
	
		init { |ratio,v1,v2,nT|
		k=2;
		r=ratio;
		a=v1;
		b=v2;
		type=this.class.name;
		neighborhoodType=nT;
		if (nT==\custom) 
			{this.setNeighborhood}
			{this.getNeighborhood};	
	}
	
	applylFunction {}
}
