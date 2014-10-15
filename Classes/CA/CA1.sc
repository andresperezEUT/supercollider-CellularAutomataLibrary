// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional cellular automata

CA1 : CA {

	*new { |instanceOfRule|	
		^super.new.initCA(instanceOfRule);	
	}
	
	initCA { |instanceOfRule=(Standard1d.new.setRule(90))|
		ruleType=instanceOfRule;
		ruleName=instanceOfRule.name;
	}
	
	run { |d,b,seed,seedProb,update,updateProb,generations,clock|	
		if (seed==\custom)
			{this.setSeedLine(d,b,update,updateProb,generations,clock)}
			{this.getSeedLine(d,seed,seedProb,b,update,updateProb,generations,clock)}
	}
	
	getSeedLine { |d,seed,seedProb,border,update,updateProb,generations,clock|
		var line;
		var d2=(d/2).floor;

		if (ruleType.isDiscrete) {
			line=switch (seed)
				{\single}		{0.dup(d).put(d2,ruleType.k-1)}
				{\random}		{Array.rand(d,0,ruleType.k-1)}
				// seedProb:Array of size k with the probabilities (P1, ... Pk-1)
				{\prob}		{ if (seedProb.isNil){seedProb=1.dup(ruleType.k).normalizeSum};
										d.collect{ruleType.k.collect{|i|i}.wchoose(seedProb)}}
				{"Incorrect seed type".warn};
		} {
			line=switch (seed)
				{\single}		{0.dup(d).put(d2,1)}
				{\random}		{Array.rand(d,0.0,1)}
				{"Incorrect seed type".warn};
		};	

		if (seedState.isNil) {seedState=List.newClear(0)};
		seedState.add(line);	
		this.runRoutine(d,border,update,updateProb,generations,clock);	
	}
	
	setSeedLine { |argX,border,update,updateProb,generations,clock|
		var cellSize=15;
		var k=ruleType.k;
		var line=0.dup(argX);
		var resLine=nil;
		var value;
		var colors=CA1View.getAllColors;
		var w = Window("custom Line", Rect(100, 500, cellSize*argX, cellSize+100)).userCanClose_(false).front;
		var b=Button.new(w,Rect(0,cellSize,30,30)).states_([["OK",Color.white,Color.black]]).action_({
			w.close;
			if (seedState.isNil) {seedState=List.newClear(0)};
			seedState.add(line);
			this.runRoutine(argX,border,update,updateProb,generations,clock);			
		});
		var u= UserView(w,Rect(0,0,cellSize*argX,cellSize)).background_(Color.black).drawFunc= {				
			argX.do{|x|		
				value=line[x];
				if(value>0) {
					Pen.fillColor= colors.at(value);
					Pen.addRect(Rect(x*cellSize, 0, cellSize,cellSize));
					Pen.fill;
				};
			};
			Pen.strokeColor=Color.green;
			forBy(0,cellSize*argX,cellSize,{|x|	Pen.moveTo(x@0); Pen.lineTo(x@(cellSize))});
			Pen.stroke;
		};
		u.mouseDownAction_({ |v,x|		
			var posX=(x/cellSize).floor;			
			var value=line.at(posX);
			if (value==(k-1)) {value=0} {value=(value+1)};
			line.put(posX,value);
			u.refresh;
		});
	}
	


//	
	resetCount {
		if (ruleType.type==\TimeDependent or:{ruleType.type==\TimeDependentTotalistic}) {ruleType.resetCount};
	}
	
	
	runRoutine{ |d,b,update,updateProb,generations,clock|
		var t=this.newClock(clock);
		var ssID=this.setID(1,generations,ruleType.isDiscrete,t);
		
		var newLine=seedState.at(ssID);
		var border=b;
		var oldLine;
		var diffLine;
		var birthLine;
		var length=d;
		var i=0;
		
		
		var index,indices;
		var dArray=(0..length-1);
		var order;
		var times, counts;
		if (pause.isNil) {pause=List.newClear(0)};
		pause.add(true);
		if (end.isNil) {end=List.newClear(0)};
		end.add(false);
		if (winFront.isNil) {winFront=List.newClear(0)};
		winFront.add(false);
		this.createGui(ssID);
		this.resetCount;	
			
		switch (update)
			{\synchronous} {	
				t.schedAbs(t.beats.ceil-delta, {
					if (pause.at(ssID)==false) {
						oldLine=newLine;
						newLine=ruleType.getNextLine(oldLine,border);
						diffLine=newLine-oldLine;
						birthLine=diffLine.collect{|v,i| if (v>0 and:{oldLine[i]==0}) {1} {0}};		
									
						this.updateSS(ssID,[newLine,diffLine,birthLine]);
						i=i+1;	
					};
					if (end.at(ssID)==false) {1}
				});
			} 
		// Random Independent:
		// one cell is randomly selected for update at each time step
			{\randomIndependent} {			
				t.schedAbs(t.beats.ceil-delta, {
					if (pause.at(ssID)==false) {
						index=length.rand;
						oldLine=newLine;
						newLine=ruleType.getNextLine(oldLine,border,index);	
						diffLine=newLine-oldLine;
						birthLine=diffLine.collect{|v,i| if (v>0 and:{oldLine[i]==0}) {1} {0}};	
						
						this.updateSS(ssID,[newLine,diffLine,birthLine]);
						i=i+1;	
					};
					if (end.at(ssID)==false) {1}
				});
			}
		// Random Order:
		// one cell is updated each time step
		// all cells must be updated before one updates again			
			{\randomOrder}		{			
				t.schedAbs(t.beats.ceil-delta, {
					if (pause.at(ssID)==false) {
						if (mod(i,length)==0) {order=dArray.scramble};
						oldLine=newLine;
						newLine=ruleType.getNextLine(oldLine,border,order[mod(i,length)]);		
						diffLine=newLine-oldLine;
						birthLine=diffLine.collect{|v,i| if (v>0 and:{oldLine[i]==0}) {1} {0}};	
						
						this.updateSS(ssID,[newLine,diffLine,birthLine]);
						i=i+1;	
					};
					if (end.at(ssID)==false) {1}
				});
			}
		// Random Update
		// random selected cells are updated each time step			
			{\randomUpdate}		{			
				t.schedAbs(t.beats.ceil-delta, {
					if (pause.at(ssID)==false) {
						indices=dArray.select{updateProb.coin};
						oldLine=newLine;
						newLine=ruleType.getNextLine(oldLine,border,indices);	
						diffLine=newLine-oldLine;
						birthLine=diffLine.collect{|v,i| if (v>0 and:{oldLine[i]==0}) {1} {0}};	
						
						this.updateSS(ssID,[newLine,diffLine,birthLine]);
						i=i+1;	
					};
					if (end.at(ssID)==false) {1}
				});
			}
		// Clock update:
		// each cell has a period of update, randomly set at beggining
			{\clock}				{			
				times=Array.newClear(length);
				counts=Array.fill(length,0);
				length.do {|i| times.put(i,(length.rand)+1)};					
				t.schedAbs(t.beats.ceil-delta, {
					if (pause.at(ssID)==false) {
						counts=counts.collect {|item,index|
							if ((item+1)==times[index])
								{item=0}
								{item=item+1}
						};
						oldLine=newLine;
						newLine=ruleType.getNextLine(oldLine,border,counts.indicesOfEqual(0));				
						diffLine=newLine-oldLine;
						birthLine=diffLine.collect{|v,i| if (v>0 and:{oldLine[i]==0}) {1} {0}};	
						
						this.updateSS(ssID,[newLine,diffLine,birthLine]);		
						i=i+1;	
					};
					if (end.at(ssID)==false) {1}
				});
			}
		// Cyclic update:
		// in each time step one cell is updated, according to a order
		// the order is fixed randomly at beggining
			{\cyclic}			{			
				order=(0..length-1).scramble;	
				t.schedAbs(t.beats.ceil-delta, {
					if (pause.at(ssID)==false) {
						oldLine=newLine;
						newLine=ruleType.getNextLine(oldLine,border,order[mod(i,length)]);		
						diffLine=newLine-oldLine;
						birthLine=diffLine.collect{|v,i| if (v>0 and:{oldLine[i]==0}) {1} {0}};	
						
						this.updateSS(ssID,[newLine,diffLine,birthLine]);	
						i=i+1;	
					};
					if (end.at(ssID)==false) {1}
				});
			};
			// Exception
//			{"Incorrect update type".warn;MethodError.new.throw};
	}	
}
