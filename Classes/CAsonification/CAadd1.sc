// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional additive synthesis

CAadd1 : CAsonification {

	var name="addsBank1-";
	var freq,freqMin,freqMax;
	var freqDist;
	var out;

	createSynth { |t,argN|
		clock=t;	
		length=argN;
		freqMin=20;
		freqMax=20000;
		freqDist=\log;
		out=0;
		
		if (SynthDescLib.global[(name++length).asSymbol].isNil) {		
			SynthDef(name++length, { |lagF=0,lagA=0,lagP=0,out=0,mute=1|
				var freq,amp,phase;
				freq = NamedControl.kr(\freq, this.expandFreq ,lagF);
				amp = NamedControl.kr(\amp, (1/length) ! length, lagA);
				phase = NamedControl.kr(\phase, 0 ! length, lagP);
				Out.ar(
					out,
			 		DynKlang.ar(`[freq,amp,phase])*mute
				 )
			}).add;
		};
	}
	
	synth {
		synth=Synth((name++length).asSymbol);
	}
	
	free {		
		connexions.do{ |v,i|
			this.removeConnexion(i);
		};
		synth.free;
	}	

	setParameter { |synthParameter,values|	
		switch (synthParameter)
			//array
			{\freq}		{values=this.expand(values)}
			{\phase}	{values=this.expand(values)}
			{\amp}		{values=this.expand(values)}
			//float
			{\tempo}	{this.changeTempo(values)}
			{\lagF}		{}
			{\lagA}		{}
			{\lagP}		{}
			{\freqMin}	{freqMin=values; synthParameter=\freq; values=this.expandFreq}
			{\freqMax}	{freqMax=values; synthParameter=\freq; values=this.expandFreq}
			{\out}		{}
			//symbol
			{\freqDist}	{freqDist=values; synthParameter=\freq; values=this.expandFreq}
			{\mute}		{values=0}
			{\unmute}	{synthParameter=\mute; values=1}
						{"Parameter not found".warn};
		
		[synthParameter,values].postln;
		synth.set(synthParameter,values);
	}
	
	expand { |val|
		var res=val;
		if (val.class.name!=\Array) {
			res=val!length;		
		}
		^res;
	
	}
	
	expandFreq {
		^this.getArray(length,freqDist,freqMin,freqMax);
	}


	getArray {|length,dist,min,max|
		var v=switch (dist)
			{\lin}	{Array.series(length,min,(max-min)/(length-1))}
			{\log}	{Array.geom(length,min,(max/min)**(1/(length-1)))};
		^v;
	}
}
