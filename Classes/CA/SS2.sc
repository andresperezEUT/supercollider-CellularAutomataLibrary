// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional state space

SS2 {

	classvar delta=0.001;
	var <caName;
	var <id;
	var <seedMatrix;
	var <newMatrix;
	var <diffMatrix;
	var <birthMatrix;
	var <isDiscrete;
	var <n=0;
	var <view;
	var <x,<y;
	var t;
	var pause=true;
	var replayCount=0;
	var steps;
	
	
	*new { |argid,argSeedMatrix,argIsDiscrete,argClock,argName|
		^super.new.init(argid,argSeedMatrix,argIsDiscrete,argClock,argName);
	}
	
	init { |argid,argSeedMatrix,argIsDiscrete,argClock,argName|
		caName=argName;
		id=argid;
		seedMatrix=argSeedMatrix;
		x=seedMatrix.cols;
		y=seedMatrix.rows;
		isDiscrete=argIsDiscrete;
		newMatrix=List.new.add(seedMatrix);
		diffMatrix=List.new.add(seedMatrix);
		birthMatrix=List.new.add(seedMatrix);
		t=argClock;
		this.replayRoutine;
	}
	
	setView { |ssID|
		view=CA2View.new(ssID,seedMatrix,isDiscrete);
	}
	
	update  { |values|
		newMatrix.add(values[0]);
		diffMatrix.add(values[1]);
		birthMatrix.add(values[2]);
		n=n+1;
		this.updateView(newMatrix.last,birthMatrix.last)
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
				CA.updateFromSS(id,[newMatrix.at(replayCount),diffMatrix.at(replayCount),birthMatrix.at(replayCount)]);
				this.updateView(newMatrix.at(replayCount),birthMatrix.at(replayCount));
				if (replayCount==steps.last) {replayCount=steps.first} {replayCount=replayCount+1};
			};
			1;
		});
	}


}
