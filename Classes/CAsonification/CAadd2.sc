// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional additive synthesis

CAadd2 : CAsonification {

	var name="addsBank2-";
	var freq,freqMin,freqMax;
	var phase,phaseMin,phaseMax;
	var freqDist;
	var phaseDist;
	var out;

	createSynth { |t,argX,argY|
		clock=t;	
		x=argX;
		y=argY;
		length=x*y;		
		freqMin=20;
		freqMax=20000;
		freqDist=\log;
		phaseMin=0;
		phaseMax=2pi;
		phaseDist=\lin;	
		out=0;
		
		if (SynthDescLib.global[(name++length).asSymbol].isNil) {		
			SynthDef(name++length, { |lagF=0,lagA=0,lagP=0,out=0,mute=1|
				var freq,amp,phase;
				freq = NamedControl.kr(\freq, this.expandFreq ,lagF);
				amp = NamedControl.kr(\amp, (1/length) ! length, lagA);
				phase = NamedControl.kr(\phase, this.expandPhase, lagP);
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
			{\freq}		{values=values.asArray}
			{\phase}	{values=values.asArray}
			{\amp}		{values=values.asArray}
			//float
			{\tempo}	{this.changeTempo(values)}
			{\lagF}		{}
			{\lagA}		{}
			{\lagP}		{}
			{\out}		{}
			{\freqMin}	{freqMin=values; synthParameter=\freq; values=this.expandFreq}
			{\freqMax}	{freqMax=values; synthParameter=\freq; values=this.expandFreq}
			{\phaseMin}	{phaseMin=values; synthParameter=\phase; values=this.expandPhase}
			{\phaseMax}	{phaseMax=values; synthParameter=\phase; values=this.expandPhase}
			{\out}		{}
			//symbol
			{\freqDist}	{freqDist=values; synthParameter=\freq; values=this.expandFreq}
			{\phaseDist}{phaseDist=values; synthParameter=\phase; values=this.expandPhase}
			{\mute}		{values=0}
			{\unmute}	{synthParameter=\mute; values=1}
						{"Parameter not found".warn};
		
		[synthParameter,values].postln;
		synth.set(synthParameter,values);
	}
	
	expandFreq {
		var values=this.getArray(y,freqDist,freqMin,freqMax);
		var new;
		values.do{|v| new=new++v.dup(x)};
		^new.reverse;
	}
	
	expandPhase {
		var values=this.getArray(x,phaseDist,phaseMin,phaseMax);
		^values.dup(y).flat;
	}

	getArray {|length,dist,min,max|
		var v=switch (dist)
			{\lin}	{Array.series(length,min,(max-min)/(length-1))}
			{\log}	{Array.geom(length,min,(max/min)**(1/(length-1)))};
		^v;
	}

}
