// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional cellular automata

CA2 : CA {

	*new { |instanceOfRule|	
		^super.new.initCA(instanceOfRule);	
	}
	
	initCA { |instanceOfRule=(Life2d.new)|	
		ruleType=instanceOfRule;
		ruleName=instanceOfRule.name;
	}
	
	run { |x=10,y=10,b,seed=\single,seedProb,clock|			
		if (seed==\custom)
			{^this.setSeedMatrix(x,y,b,clock)}
			{^this.getSeedMatrix(x,y,seed,seedProb,b,clock)}
	}
	
	getSeedMatrix { |x,y,seed,seedProb,border,clock|
		var matrix;
		var x2=(x/2).floor, y2=(y/2).floor;

		if (ruleType.isDiscrete) {
			if (seed==\single)	{ matrix=Array2D.fromArray(y,x,0.dup(x*y)).put(y2,x2,ruleType.k-1) };
			if (seed==\block)	{ matrix=Array2D.fromArray(y,x,0.dup(x*y)).put(y2,x2,ruleType.k-1).put(y2-1,x2,ruleType.k-1).put(y2,x2-1,ruleType.k-1).put(y2-1,x2-1,ruleType.k-1) };
			if (seed==\random)	{ matrix=Array2D.fromArray(y,x,Array.rand(x*y,0,ruleType.k-1)) };
			if (seed==\prob)	{ if (seedProb.isNil) {seedProb=1.dup(ruleType.k).normalizeSum };
									matrix=Array2D.fromArray(y,x,(x*y).collect{ruleType.k.collect{|i|i}.wchoose(seedProb)})};	
		} {				
			if (seed==\single)	{ matrix=Array2D.fromArray(y,x,0.dup(x*y)).put(y2,x2,1) };					
			if (seed==\random)	{ matrix=Array2D.fromArray(y,x,Array.rand(x*y,0.0,1)) };
		};
		if (seedState.isNil) {seedState=List.newClear(0)};
		seedState.add(matrix);	
		
		^this.runRoutine(border,clock);	
	}
	
	setSeedMatrix { |argX,argY,border,clock|
		var cellSize=15;
		var k=ruleType.k;
		var matrix=Array2D.fromArray(argY,argX,0.dup(argX*argY));
		var resMatrix=nil;
		var value;
		var colors=CA2View.getAllColors;
		var w = Window("custom Matrix", Rect(100, 500, cellSize*argX, cellSize*argY+100)).userCanClose_(false).front;
		var b=Button.new(w,Rect(0,cellSize*argY,30,30)).states_([["OK",Color.white,Color.black]]).action_({
			w.close;
			if (seedState.isNil) {seedState=List.newClear(0)};
			seedState.add(matrix);
			^this.runRoutine(border,clock);			
		});
		var u= UserView(w,Rect(0,0,cellSize*argX,cellSize*argY)).background_(Color.black).drawFunc= {
			argY.do{|y|				
				argX.do{|x|		
					value=matrix.at(y,x);			
					if(value>0) {
						Pen.fillColor= colors.at(value);
						Pen.addRect(Rect(x*cellSize, y*cellSize, cellSize,cellSize));
						Pen.fill;
					};
				};
			};	
			Pen.strokeColor=Color.green;
			forBy(0,cellSize*argX,cellSize,{|x|	Pen.moveTo(x@0); Pen.lineTo(x@(cellSize*argY))});
			forBy(0,cellSize*argY,cellSize,{|y| Pen.moveTo(0@y); Pen.lineTo((cellSize*argX)@y)});
			Pen.stroke;
		};
		u.mouseDownAction_({ |v,x,y|
		
			var posX=(x/cellSize).floor;
			var posY=(y/cellSize).floor;
			
			var value=matrix.at(posY,posX);
			if (value==(k-1)) {value=0} {value=(value+1)};
			matrix.put(posY,posX,value);
			u.refresh;
		});
	}
	
	runRoutine { |b,clock|

		var t=this.newClock(clock);
		var ssID=this.setID(2,isDiscrete:ruleType.isDiscrete,clock:t);	
		
		var newMatrix=seedState.at(ssID);
		var border=b;
		var oldMatrix;
		var diffMatrix;
		var birthMatrix;
		var i=0;
		
		if (pause.isNil) {pause=List.newClear(0)};
		pause.add(true);
		if (end.isNil) {end=List.newClear(0)};
		end.add(false);
		if (winFront.isNil) {winFront=List.newClear(0)};
		winFront.add(false);
		this.createGui(ssID);


		t.schedAbs(t.beats.ceil-delta, {
			if (pause.at(ssID)==false) {
				oldMatrix=newMatrix;
				newMatrix=ruleType.getNextMatrix(oldMatrix,border,i);
				diffMatrix=newMatrix.minus(oldMatrix);
				birthMatrix=this.getBirthMatrix(newMatrix,oldMatrix);
				
				this.updateSS(ssID,[newMatrix,diffMatrix,birthMatrix]);
				i=i+1;	
			};
			if (end.at(ssID)==false) {1}
		});	
		^ssID;
	}
	
	getBirthMatrix { |new,old|
		var res=Array2D.fromArray(new.rows,new.cols,0.dup(new.rows*new.cols));
		res.cols.do {|col|
			res.rows.do {|row|
				if (new.at(row,col)>0 and: {old.at(row,col)==0})
					{res.put(row,col,1)}
			}
		};
		^res;
	}
	
}
