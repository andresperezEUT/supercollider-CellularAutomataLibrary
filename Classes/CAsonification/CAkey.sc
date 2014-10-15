// Andrés Pérez, 2011
// www.andresperezlopez.com

// keyboard-like synthesis (1D)

CAkey : CAsonification {

	var player;
	var instrument;
	var degree;
	var scale;
	var amp;
	var pan;
	var legato;
	var inDegrees;
	var freq;
	var freqMin;
	var freqMax;
	var freqDist;
	var out;

	
	createSynth {|t,l|
		clock=t;
		length=l;
			
		instrument=\default;
		degree=(0..length-1);
		scale=Scale.minor;
		amp=0.2!length;
		pan=0!length;
		legato=0.5!length;
		freqMin=100;
		freqMax=1000;
		freqDist=\lin;
		freq=this.expandFreq;		
		inDegrees=true;
		out=0!length;
		
		player=Pif(Pfunc({inDegrees}),
		 Pbind(
			\instrument,Pfunc({instrument}),
			\degree,degree,
			\scale,Pfunc({scale}),
			\amp,amp,
			\pan,pan,
			\legato,legato,
			\out,out,
			\dur,1
		 ),
		 Pbind(
			\instrument,Pfunc({instrument}),
			\freq,freq,
			\amp,amp,
			\pan,pan,
			\legato,legato,
			\out,out,
			\dur,1
		 )
		).asEventStreamPlayer;
	}
	
	synth {
		player.play(clock,quant:1);
	}
	
	free {		
		connexions.do{ |v,i|
			this.removeConnexion(i);
		};
		player.stop;
	}

	setParameter { |synthParameter,values|
		[synthParameter,values].postln;

		values=this.expand(values);		
		switch (synthParameter)	
			//array
			{\amp}			{this.replace(amp,values)}
			{\pan}			{this.replace(pan,values)}
			{\legato}		{this.replace(legato,values)}
			{\degree}		{this.replace(degree,values)}
			{\freq}			{this.replace(freq,values)}
			//float
			{\freqMin}		{freqMin=values.at(0); this.replace(freq,this.expandFreq)}
			{\freqMax}		{freqMax=values.at(0); this.replace(freq,this.expandFreq)}
			{\out}			{this.replace(out,values)}	
			//symbol-array
			{\scale}		{if (values.class.name==\Symbol) 
								{scale=ScaleInfo.scales.at(values)} 
								{scale=Scale.new(values)}}
			//symbol
			{\instrument}	{instrument=values}
			{\pitchMode}	{switch(values) {\degree}{inDegrees=true} {\freq}{inDegrees=false}}
			{\freqDist}		{freqDist=values; this.replace(freq,this.expandFreq)}
			//float
			{\tempo}		{this.changeTempo(values.at(0))}
			//unary			
			{\mute}			{player.mute}
			{\unmute}		{player.unmute}		
							{"Parameter not found".warn};
	}
	
	replace { |parameter,values|
		parameter.do{|v,i| parameter.put(i,values[i])};
	}

	expand {|values|
		if (values.class.superclass.name==\SimpleNumber) {values=values!length};
		^values;
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
