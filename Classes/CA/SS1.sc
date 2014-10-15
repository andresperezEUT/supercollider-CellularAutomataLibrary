// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional state space

SS1 {

	classvar delta=0.001;
	var <caName;
	var <id;
	var <seedLine;
	var <newLine;
	var <diffLine;
	var <birthLine;
	var <isDiscrete;
	var <n=0;
	var <view;
	var <length;
	var <generations;
	var <t;
	var pause=true;
	var replayCount=0;
	var steps;
	
	*new { |argid,argSeedLine,argGenerations,argIsDiscrete,argClock,argName|
		^super.new.init(argid,argSeedLine,argGenerations,argIsDiscrete,argClock,argName);
	}
	
	init { |argid,argSeedLine,argGenerations,argIsDiscrete,argClock,argName|
		caName=argName;
		id=argid;
		seedLine=argSeedLine;
		length=seedLine.size;
		generations=argGenerations;
		isDiscrete=argIsDiscrete;
		newLine=List.new.add(seedLine);
		diffLine=List.new.add(seedLine);
		birthLine=List.new.add(seedLine);		
		t=argClock;
		this.replayRoutine;
	}
	
	setView { |ssID|
		view=CA1View.new(ssID,seedLine,generations,isDiscrete);
	}
	
	update  { |values|
		newLine.add(values[0]);
		diffLine.add(values[1]);
		birthLine.add(values[2]);
		n=n+1;
		this.updateView(newLine.last,birthLine.last)
	}
	
	updateView { |new,birth|
		if (view.isNil.not)	{
			SystemClock.sched(Server.default.latency, {
				view.update(new,birth);
			})
		};
	}
	
	replay { |init,end|
		steps=(init..end);
		replayCount=init;
		pause=false;
	}
	
	pause {
		pause=true;
	}
	
	replayRoutine {
		t.schedAbs(t.beats.ceil-delta, {
			if (pause==false) {
				CA.updateFromSS(id,[newLine.at(replayCount),diffLine.at(replayCount),birthLine.at(replayCount)]);
				this.updateView(newLine.at(replayCount),birthLine.at(replayCount));
				if (replayCount==steps.last) {replayCount=steps.first} {replayCount=replayCount+1};
			};
			1;
		});
	}
}
