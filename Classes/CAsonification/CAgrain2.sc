// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional granular synthesis

CAgrain2 : CAgrain {
	
	createSynth { |t,argX,argY|
		clock=t;
		x=argX;
		y=argY;
		length=x*y;
		basefreq=100;
		gdur=1;
		gamp=1;
		pdelay=(0..x-1)*gdur/x;
		pdur=Array2D.fromArray(y,x,1!length);
		pamp=Array2D.fromArray(y,x,(1/length)!length);
		pan=Array2D.fromArray(y,x,0!length);
		waveform=\grainSin;
		waveformDict=Dictionary[\sin->\grainSin, \tri->\grainTri, \trap->\grainTrap, \exp->\grainExp, \rexp->\grainRexp];
		wavenumDict=Dictionary[0->\grainSin, 1->\grainTri, 2->\grainTrap, 3->\grainExp, 4->\grainRexp];
		mute=1;
		gprop=0.9;
		pprop=0.99;
		curve=1;
		asynchronous=false;
		if (SynthDescLib.global[\grainSin].isNil) {this.createSynthDefs};
	}

	synth {
		Routine({ 
			var timeToNextBeat,delta,wait;
			var tempo,gdurR,pdurR,pdelayR;
			while ({end==false},{
				tempo=clock.tempo;
				gdurR=gdur/tempo;
				pdurR=pdur.mul(gdurR/x);	
				pdelayR=(0..x-1)*gdurR/x;

				Server.default.bind {
					x.do { |col|
						y.do { |row|
							Synth.grain(waveform,[
								\freq,basefreq*(y-row),
								\gamp,gamp,
								\gdur,gdurR,
								\pdelay,pdelayR.at(col),
								\pdur,pdurR.at(row,col),
								\pamp,pamp.at(row,col),
								\pan,pan.at(row,col),
								\mute,mute,
								\pprop,pprop,
								\gprop,gprop,
								\curve,curve]);
						}
					}
				};
				
				if (asynchronous) {
					delta=exprand(0.001,0.999); 
					timeToNextBeat=clock.elapsedBeats.ceil-clock.elapsedBeats;
					wait=timeToNextBeat+delta;
				} {
					wait=1
				};			
				
				wait.wait;
			})
		}).play(clock,quant:1);
	}
	

	expand {|values,direction|
		var array;
		values=switch (values.class.name)
			{\Array2D}	{values}
			{\Array}	{ if (direction==\h) {
							Array2D.fromArray(y,x,values.dup(y).flat);
						} {
							values.do{|v| array=array++v.dup(x)};
							array=array.reverse;
							Array2D.fromArray(y,x,array);
						}
			}
			{\Integer}	{Array2D.fromArray(y,x,values!length)}
			{\Float}	{Array2D.fromArray(y,x,values!length)};
		^values;
	}

}
