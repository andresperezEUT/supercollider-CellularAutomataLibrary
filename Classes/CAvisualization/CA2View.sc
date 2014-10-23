// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional cellular automata view class

CA2View {

	var ssID;
	var i=0;
	var <seedMatrix;
	var matrix;
	var births=false, birthsMatrix;
	var ss,ssU,ssFront=false;
	var winSizeX,winSizeY;
	var cols=10;
	var rows=10;
	var grid=false; 
	var colors;
	var type;
	
	
	*new { |argssID,argSeed,argIsDiscrete|
		^super.new.init(argssID,argSeed,argIsDiscrete);	
	}
	
	
	init { |argssID,argSeed,argIsDiscrete|
		ssID=argssID;
		seedMatrix=argSeed;
		matrix=seedMatrix;
		rows=matrix.rows;
		cols=matrix.cols;
		winSizeX=100;
		winSizeY=100;
		colors=this.class.getAllColors;
		birthsMatrix=Array2D.fromArray(rows,cols,0.dup(rows*cols));
		if (argIsDiscrete)
			{type=\Discrete}
			{type=\Continuous};
		
		ss=Window.new("SS"++ssID,Rect(128,64,winSizeX,winSizeY)).userCanClose_(true).alwaysOnTop_(true);
		ssU= UserView(ss,ss.view.bounds.insetBy(0,0)).resize_(5).background_(Color.black).drawFunc= {
			var winSizeX=ss.view.bounds.right;
			var winSizeY=ss.view.bounds.bottom;
			var cellSizeX=(winSizeX/cols).floor;
			var cellSizeY=(winSizeY/rows).floor;
			var value;
			if (ssFront) {
				cols.do{|x|				
					rows.do{|y|		
						value=matrix.at(y,x);
						if(value>0) {
						if (births and:{birthsMatrix.at(y,x)==1}) {
							Pen.fillColor=Color.white;						
						} {
							if (type==\Continuous) 
								{Pen.fillColor=Color.new(value,0,0)}
								{Pen.fillColor= colors[value]};	
						};				
							Pen.addRect(Rect(x*cellSizeX, y*cellSizeY, cellSizeX,cellSizeY));
							Pen.fill;
						};
					};
				};	
				if (grid) {
					Pen.strokeColor=Color.green(alpha:0.4);
					forBy(0,cellSizeX*cols,cellSizeX,{|x| Pen.moveTo(x@0); Pen.lineTo(x@(cellSizeY*rows))});
					forBy(0,cellSizeY*rows,cellSizeY,{|y| Pen.moveTo(0@y); Pen.lineTo((cellSizeX*cols)@y)});
					Pen.stroke;
				}
			};
		ssU.refresh;
		}
	}
	
	hss {
	
	}

	ss {
		ss.front;
		if (ssFront)
			{ss.minimize}
			{ss.unminimize};
		ssFront=ssFront.not;
	}
	
	update {|argMatrix,argBirthsMatrix|
		matrix=argMatrix;
		birthsMatrix=argBirthsMatrix;	
		{
		ssU.refresh;
		}.defer;
	}	

	
	setBirths {
		births=births.not;
		ssU.refresh;
	}

	setGrid {
		grid=grid.not;
		ssU.refresh;
	}
	
//	setSeedLine {
//		line=seedLine;
//		hssU.refresh;
//		ssU.refresh;
//	}
	
	close {
		ss.close;
	}
	
	
	*getAllColors {
		^Array.with(Xcolors.get('black'),Xcolors.get('red'),Xcolors.get('green'),Xcolors.get('green yellow'),Xcolors.get('yellow'),Xcolors.get('orange'),Xcolors.get('sky blue'),Xcolors.get('violet'),Xcolors.get('alice blue'),Xcolors.get('gray55'),Xcolors.get('gray50'),Xcolors.get('gray45'),Xcolors.get('gray40'),Xcolors.get('gray35'),Xcolors.get('gray30'),Xcolors.get('gray25'),Xcolors.get('gray20'),Xcolors.get('gray15'),Xcolors.get('gray10'),Xcolors.get('gray5'));
	}	

}
