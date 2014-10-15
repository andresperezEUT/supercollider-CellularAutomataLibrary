// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional cellular automata view class

CA1View {

	var ssID;
	var i=0;
	var <seedLine;
	var line;
	var births=false, birthsLine;
	var historyMatrix;
	var hss,hssU,hssFront=false;
	var ss,ssU,ssFront=false;
	var cellSize=15;
	var winSizeX,winSizeY;
	var length;
	var rows;
	var grid=false; 
	var colors;
	var type;
	
	
	*new { |argssID,argSeed,argGenerations,argIsDiscrete|
		^super.new.init(argssID,argSeed,argGenerations,argIsDiscrete);	
	}
	
	init { |argssID,argSeed,argGenerations,argIsDiscrete|
		ssID=argssID;
		seedLine=argSeed;
		length=argSeed.size;
		line=seedLine;
		if (argGenerations.isNil) {rows=10} {rows=argGenerations};
		if (argIsDiscrete)
			{type=\Discrete}
			{type=\Continuous};
		winSizeX=300;
		winSizeY=300;
		colors=this.class.getAllColors;
		ss=Window.new("SS"++ssID,Rect(250,64,winSizeX,cellSize)).userCanClose_(true).alwaysOnTop_(true);
		ssU= UserView(ss,ss.view.bounds.insetBy(0,0)).resize_(5).background_(Color.black).drawFunc= {
			var winSizeX=ss.view.bounds.right;
			var winSizeY=ss.view.bounds.bottom;
			var cellSizeX=(winSizeX/length).floor;
			var cellSizeY=(winSizeY).floor;
			var value;
			if (ssFront) {
			
				length.do{|x|					
					value=line.at(x);
					if(value>0) {
							if (type==\Continuous) 
								{Pen.fillColor=Color.new(value,0,0)}
								{Pen.fillColor= colors[value]};				
						Pen.addRect(Rect(x*cellSizeX, 0, cellSizeX,cellSizeY));
						Pen.fill;
					};
				};
				Pen.strokeColor=Color.green(alpha:0.4);
				forBy(0,cellSizeX*length,cellSizeX,{|x| Pen.moveTo(x@0); Pen.lineTo(x@(cellSizeY))});
				Pen.stroke;	
			};
		};
		ssU.refresh;
		
		
		historyMatrix=Array2D.fromArray(rows,length,0.dup(rows*length)).putRow(0,seedLine);
		hss=Window.new("History SS"++ssID,Rect(128,64,winSizeX,winSizeY)).userCanClose_(true).alwaysOnTop_(true);
		hssU= UserView(hss,hss.view.bounds.insetBy(0,0)).resize_(5).background_(Color.black).drawFunc= {
			var winSizeX=hss.view.bounds.right;
			var winSizeY=hss.view.bounds.bottom;
			var cellSizeX=(winSizeX/length).floor;
			var cellSizeY=(winSizeY/rows).floor;
			var value;
			if (hssFront) {
				length.do{|x|		
					rows.do{|y|		
						value=historyMatrix.at(y,x);
						if(value>0) {
							if (y==i  and: {births and: {birthsLine.at(x)==1}}) {
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
				Pen.strokeColor=Color.white;
				Pen.line(0@(i*cellSizeY),(length*cellSizeX)@(cellSizeY*i));
				Pen.line(0@((i+1)*cellSizeY),(length*cellSizeX)@(cellSizeY*(i+1)));
				Pen.stroke;
				if (grid) {
					Pen.strokeColor=Color.green(alpha:0.4);
					forBy(0,cellSizeX*length,cellSizeX,{|x| Pen.moveTo(x@0); Pen.lineTo(x@(cellSizeY*rows))});
					forBy(0,cellSizeY*rows,cellSizeY,{|y| Pen.moveTo(0@y); Pen.lineTo((cellSizeX*length)@y)});
					Pen.stroke;
				}
			};
		};
		hssU.refresh;
	}
	
	hss {
		hss.front;
		if (hssFront)
			{hss.minimize}
			{hss.unminimize};
		hssFront=hssFront.not;
	}
	ss {
		ss.front;
		if (ssFront)
			{ss.minimize}
			{ss.unminimize};
		ssFront=ssFront.not;
	}
	

	update {|argLine,argBirthsLine|
		this.updateCount;		
		line=argLine;
		historyMatrix.putRow(i,line);
		birthsLine=argBirthsLine;
		
		hssU.refresh;
		ssU.refresh;
	}	
	
	updateCount {
		if (i==(rows-1))
			{i=0}
			{i=i+1}
	}
	
	setBirths {
		births=births.not;
		hssU.refresh;
	}

	setGrid {
		grid=grid.not;
		hssU.refresh;
	}
	
	setSeedLine {
		line=seedLine;
		hssU.refresh;
		ssU.refresh;
	}
	
	close {
		hss.close;
		ss.close;
	}
	
	
	*getAllColors {
		^Array.with(Xcolors.get('black'),Xcolors.get('red'),Xcolors.get('green'),Xcolors.get('green yellow'),Xcolors.get('yellow'),Xcolors.get('orange'),Xcolors.get('sky blue'),Xcolors.get('violet'),Xcolors.get('alice blue'),Xcolors.get('gray55'),Xcolors.get('gray50'),Xcolors.get('gray45'),Xcolors.get('gray40'),Xcolors.get('gray35'),Xcolors.get('gray30'),Xcolors.get('gray25'),Xcolors.get('gray20'),Xcolors.get('gray15'),Xcolors.get('gray10'),Xcolors.get('gray5'));
	}	

}
