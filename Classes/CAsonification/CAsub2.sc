// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional substractive synthesis

CAsub2 : CAsonification {
	
	var name="subsBank2-";
	var freq,freqMin,freqMax;
	var ring,ringMin,ringMax;
	var freqDist;
	var ringDist;
	var out;
	
	createSynth { |t,argX,argY|
		clock=t;	
		x=argX;
		y=argY;
		length=x*y;		
		freqMin=20;
		freqMax=20000;
		freqDist=\log;
		ringMin=0.1;
		ringMax=10;
		ringDist=\log;		
		out=0;
		
		if (SynthDescLib.global[(name++length).asSymbol].isNil) {		
			SynthDef(name++length, { |lagF=0,lagA=0,lagR=0,out=0,mute=1|
				var freq,amp,ring;
				 freq = NamedControl.kr(\freq, this.expandFreq ,lagF);
				 amp = NamedControl.kr(\amp, (1/length) ! length, lagA);
				 ring = NamedControl.kr(\ring,this.expandRing, lagR);
				 Out.ar(
				 	out,
					DynKlank.ar(`[freq,amp,ring],{WhiteNoise.ar(0.01)})*mute
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
			{\ring}		{values=values.asArray}
			{\amp}		{values=values.asArray}
			//float
			{\tempo}	{this.changeTempo(values)}
			{\lagF}		{}
			{\lagA}		{}
			{\lagR}		{}
			{\freqMin}	{freqMin=values; synthParameter=\freq; values=this.expandFreq}
			{\freqMax}	{freqMax=values; synthParameter=\freq; values=this.expandFreq}
			{\ringMin}	{ringMin=values; synthParameter=\ring; values=this.expandRing}
			{\ringMax}	{ringMax=values; synthParameter=\ring; values=this.expandRing}
			{\out}		{}
			//symbol
			{\freqDist}	{freqDist=values; synthParameter=\freq; values=this.expandFreq}
			{\ringDist}	{ringDist=values; synthParameter=\ring; values=this.expandRing}
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
	
	expandRing {
		var values=this.getArray(x,ringDist,ringMin,ringMax);
		^values.dup(y).flat;
	}

	getArray {|length,dist,min,max|
		var v=switch (dist)
			{\lin}	{Array.series(length,min,(max-min)/(length-1))}
			{\log}	{Array.geom(length,min,(max/min)**(1/(length-1)))};
		^v;
	}

}
