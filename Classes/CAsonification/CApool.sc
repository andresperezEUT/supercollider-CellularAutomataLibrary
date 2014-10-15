// Andrés Pérez, 2011
// www.andresperezlopez.com

// main abstract custom synthesis class

CApool : CAsonification {

	var instrument;
	var amp;
	var pan;
	var freq;
	var freqMin;
	var freqMax;
	var freqDist;
	var out;	
	var mute=false;
	var end=false;
	
	var parameterList;
	var valueList;
	var synths;

	synth {	
		this.setInstrument(instrument);	
		Routine({
			while ({end==false},{
				if (mute==false) {
					length.do { |i|
						Server.default.bind{
							parameterList.size.do { |j|
								var synthParameter=parameterList.at(j);
								var value=valueList.at(j).at(i);
								synths.at(i).set(synthParameter,value);
								synths.at(i).set(\trig,1);
								synths.at(i).set(\trig,0);
								synths.at(i).run(true);
							}
						}					
					};
					1.wait;	
				}		
			})		
		}).play(clock,quant:1);
	}
	
	free {
		length.do { |i| 
			synths.at(i).free;
		};
		end=true;
	}
	
	getParameters {
		parameterList.postln;
	}
	
	setInstrument { |instrument|
		length.do { |i|
			if (synths.at(i).isNil.not) {synths.at(i).free};
			synths.put(i,Synth.newPaused(instrument));
		}
	}
	
	getArray { |length,dist,min,max|
		var v=switch (dist)
			{\lin}	{Array.series2(length,min,max)}
			{\log}	{Array.geom2(length,min,max)};
		^v;
	}

}
