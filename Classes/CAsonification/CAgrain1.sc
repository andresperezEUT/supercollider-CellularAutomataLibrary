// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional granular synthesis

CAgrain1 : CAgrain {
	
	createSynth { |t,l|
		clock=t;
		length=l;
		basefreq=100;
		gdur=1/t.tempo;
		gamp=1;
		pdur=(1!length)/clock.tempo;
		pamp=(1/length)!length;
		pan=0!length;
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
			var tempo,gdurR,pdurR;
			while ({end==false},{
				tempo=clock.tempo;
				gdurR=gdur/tempo;
				pdurR=pdur*gdurR;	
				
				Server.default.bind{
					length.do { |i|
						Synth.grain(waveform,[
							\freq,basefreq*(i+1),
							\gamp,gamp,
							\gdur,gdurR,
							\pdur,pdurR.at(i),
							\pamp,pamp.at(i),
							\pan,pan.at(i),
							\mute,mute,
							\pprop,pprop,
							\gprop,gprop,
							\curve,curve]);
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
	
	expand {|values|
		if (values.class.superclass.name==\SimpleNumber) {values=values!length};
		^values;
	}

}
