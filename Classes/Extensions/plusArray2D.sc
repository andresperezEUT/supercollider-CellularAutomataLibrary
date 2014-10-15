// Andrés Pérez, 2011
// www.andresperezlopez.com

//some mathematical methods

+ Array2D {

	putRow { |row,array|
		cols.do { |col|
			this.put(row,col,array[col])		
		}	
	}
		
	asMatrix {
		this.rows.do {|index| this.rowAt(index).join.postln;}
	}
	
	borderAt { |row,col,border|
		var r=this.rows;
		var c=this.cols;
		if (row<0 or: {row>=r or: {col<0 or: {col>=c}}})
			{^border}
			{^this.at(row,col)}
	}
	
	borderPut { |row,col,value,border|
		var r=this.rows;
		var c=this.cols;
		if (row>=0 and: {row<r and: {col>=0 and: {col<c}}})
			{this.put(row,col,value)}
	}
	
	periodicAt { |row,col|
		var r=this.rows;
		var c=this.cols;
		if (row<0)  {row=r+row};
		if (row>=r) {row=mod(row,r)};
		if (col<0)  {col=c+col};
		if (col>=c) {col=mod(col,c)};
		^this.at(row,col);
	}
	
	periodicPut { |row,col,value|
		var r=this.rows;
		var c=this.cols;
		if (row<0)  {row=r+row};
		if (row>=r) {row=mod(row,r)};
		if (col<0)  {col=c+col};
		if (col>=c) {col=mod(col,c)};
		this.put(row,col,value);
	}
	
	minus {|m|
		var res=Array2D.new(this.rows,this.cols);
		this.rows.do {|row|
			res.putRow(row,this.rowAt(row)-m.rowAt(row));
		};
		^res;		
	}
	
	getTranspose {
		var res=Array2D.fromArray(this.cols,this.rows,nil.dup(this.rows*this.cols));
		this.rows.do { |row|
			this.cols.do { |col|
				res.put(col,row,this.at(row,col));
			}
		};
		^res;
	}
	
	mul { |p|
		var res=Array2D.new(this.rows,this.cols);
		this.rows.do { |row|
			this.cols.do { |col|
				res.put(row,col,this.at(row,col)*p);
			}
		};
		^res;
	}
	
	add { |p|
		var res=Array2D.new(this.rows,this.cols);
		this.rows.do { |row|
			this.cols.do { |col|
				res.put(row,col,this.at(row,col)+p);
			}
		};
		^res;
	}
	
}

