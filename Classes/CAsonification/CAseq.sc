// Andrés Pérez, 2011
// www.andresperezlopez.com

// sequencer-like synthesis (1D)

CAseq : CAsonification {

	var player;
	var degree;
	var freq;
	var amp;
	var pan;
	var legato;
	var inDegrees;
	var instrument;
	var scale;
	var buffer;
	var rate;
	var samples;
	var out;


	createSynth { |t,l|
		clock=t;
		length=l;
		samples=this.getSamples;		
		degree=PatternProxy(Pseq([0],inf));
		freq=PatternProxy(Pseq([440],inf));
		amp=PatternProxy(Pseq([0.2],inf));
		pan=PatternProxy(Pseq([0],inf));
		legato=PatternProxy(Pseq([1],inf));
		buffer=PatternProxy(Pseq([samples.at(0)],inf));
		rate=PatternProxy(Pseq([1],inf));
		inDegrees=true;
		instrument=\default;
		scale=Scale.minor;
		out=PatternProxy(Pseq([0],inf));

		player=Pif(Pfunc({inDegrees}),
		 Pbind(
			\instrument,Pfunc({instrument}),
			\degree,degree,
			\scale,Pfunc({scale}),
			\amp,amp,
			\pan,pan,
			\legato,legato,
			\bufnum,buffer,			
			\rate,rate,
			\out,out,
			\dur,1/length
		 ),
		 Pbind(
			\instrument,Pfunc({instrument}),
			\freq,freq,
			\amp,amp,
			\pan,pan,
			\legato,legato,
			\bufnum,buffer,
			\rate,rate,
			\out,out,
			\dur,1/length
		 )
		).asEventStreamPlayer;
		
		if (SynthDescLib.global[\bufPlay].isNil) {
			SynthDef(\bufPlay, {| out=0, bufnum, amp=0.5, rate=1, pan=0|
				Out.ar(out, 
					Pan2.ar(PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum)*rate, doneAction:2)*amp*2,pan,1)
				)
			}).store;	
		}
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
			{\degree}		{degree.source=Pseq(values,inf)}			
			{\freq}			{freq.source=Pseq(values,inf)}			
			{\amp}			{amp.source=Pseq(values,inf)}
			{\pan}			{pan.source=Pseq(values,inf)}
			{\legato}		{legato.source=Pseq(values,inf)}
			{\buffer}		{values=values.collect{|v| samples.at(v)}; buffer.source=Pseq(values,inf)}
			{\rate}			{rate.source=Pseq(values,inf)}
			{\tempoArray}	{this.changeTempoArray(values)}
			//symbol-array
			{\scale}		{if (values.class.name==\Symbol) 
								{scale=ScaleInfo.scales.at(values)} 
								{scale=Scale.new(values)}}
			//symbol
			{\instrument}	{instrument=values}
			{\pitchMode}	{switch(values) {\degree}{inDegrees=true} {\freq}{inDegrees=false}}
			//float
			{\tempo}		{this.changeTempo(values.at(0))}
			{\out}			{out.source=Pseq(values,inf)}
			//unary
			{\mute}			{player.mute}
			{\unmute}		{player.unmute}					
			{\setSample}	{this.setSample(values)}
							{"Parameter not found".warn};
	}		
	
	expand { |values|
		if (values.class.superclass.name==\SimpleNumber) {values=[values]};
		^values;
	}
	
//	changeTempo { |tempo|
//		clock.schedAbs(clock.beats.ceil,{clock.tempo_(tempo)});
//	}
	
	changeTempoArray { |tempoArray|
		length.do{ |i|
			clock.schedAbs(clock.beats.ceil+(i/length),{clock.tempo_(tempoArray.at(i))});
		};
	}
	
	// default samples
	
//	getSamples {
//		^List.with(Buffer.read(Server.default,"./samples/BassDrums1/bassdrum10.wav"),Buffer.read(Server.default,"./samples/SnareDrums1/snaredrum20.wav"),Buffer.read(Server.default,"./samples/HiHats1/hihat1.wav"),Buffer.read(Server.default,"./samples/Cowbells/cowbell1.wav"),Buffer.read(Server.default,"./samples/Tambourines/tambourine1.wav"),Buffer.read(Server.default,"./samples/Rattles/rattle1.wav"));
//	}
	
	getSamples {
		var list=List.newClear(length);
		length.do { |i| list.put(i,Buffer.alloc(Server.default,Server.default.sampleRate))};
		^list;
	}
	
	setSample { |index|
		samples.at(index.at(0)).free;
		samples.put(index.at(0),Buffer.loadDialog(Server.default));
		samples.postln;
	}

}
