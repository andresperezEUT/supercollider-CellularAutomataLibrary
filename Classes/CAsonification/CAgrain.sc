// Andrés Pérez, 2011
// www.andresperezlopez.com

// main abstract granular synthesis class

CAgrain : CAsonification {

	var basefreq;
	var gdur;
	var gamp;
	var pdelay;
	var pdur;
	var pamp;
	var pan;
	var mute;
	var asynchronous;
	var waveform;
	var waveformDict,wavenumDict;
	var gprop,pprop;
	var curve;
	var end=false;
	var out;
	
	
	setParameter { |synthParameter,values,direction|	
		switch (synthParameter)
			//n-dimensional
			{\pdur}			{pdur=this.expand(values,direction)}
			{\pamp}			{pamp=this.expand(values,direction)}
			{\pan}			{pan=this.expand(values,direction)}
			//simpleNumber
			{\freq}			{basefreq=values}
			{\gamp}			{gamp=values}
			{\gdur}			{gdur=values}
			{\pprop}		{pprop=values}	
			{\gprop}		{gprop=values}
			{\curve}		{curve=values}		
			{\tempo}		{this.changeTempo(values)}	
			{\out}			{out=values}			
			//symbol-integer		
			{\env}			{if (values.class.name==\Symbol) 
								{waveform=waveformDict.at(values)}
								{waveform=wavenumDict.at(values)}}			
			//unary
			{\mute}			{mute=0}
			{\unmute}		{mute=1}		
			{\synchronous}	{asynchronous=false}
			{\asynchronous} {asynchronous=true}	
							{"Parameter not found".warn};	
	}

	free {
		end=true;
	}

	createSynthDefs {
		SynthDef(\grainSin, { |freq=100,gdur=1,gamp=1,pdelay=0,pdur=1,pamp=1,out=0,pan=0,mute=1,pprop=0.99|		
			var pEnv=EnvGen.kr(Env.linen((1-pprop)*pdur/2,pprop*pdur,(1-pprop)*pdur/2,pamp).delay(pdelay),doneAction:2);
			var signal=SinOsc.ar(freq,mul:pEnv);
			var gEnv=EnvGen.kr(Env.sine(gdur,gamp),doneAction:2)*mute;
			Out.ar(
				out,
				Pan2.ar(signal*gEnv,pan,1)
			)			
		}).store;
		SynthDef(\grainTri, { |freq=100,gdur=1,gamp=1,pdelay=0,pdur=1,pamp=1,out=0,pan=0,mute=1,pprop=0.99|		
			var pEnv=EnvGen.kr(Env.linen((1-pprop)*pdur/2,pprop*pdur,(1-pprop)*pdur/2,pamp).delay(pdelay),doneAction:2);
			var signal=SinOsc.ar(freq,mul:pEnv);
			var gEnv=EnvGen.kr(Env.triangle(gdur,gamp),doneAction:2)*mute;
			Out.ar(
				out,
				Pan2.ar(signal*gEnv,pan,1)
			)			
		}).store;
		SynthDef(\grainTrap, { |freq=100,gdur=1,gamp=1,pdelay=0,pdur=1,pamp=1,out=0,pan=0,mute=1,gprop=0.9,pprop=0.99|			
			var pEnv=EnvGen.kr(Env.linen((1-pprop)*pdur/2,pprop*pdur,(1-pprop)*pdur/2,pamp).delay(pdelay),doneAction:2);
			var signal=SinOsc.ar(freq,mul:pEnv);	
			var gEnv=EnvGen.kr(Env.linen((1-gprop)*gdur/2,gprop*gdur,(1-gprop)*gdur/2,gamp),doneAction:2)*mute;
			Out.ar(
				out,
				Pan2.ar(signal*gEnv,pan,1)
			)			
		}).store;
		SynthDef(\grainExp, { |freq=100,gdur=1,gamp=1,pdelay=0,pdur=1,pamp=1,out=0,pan=0,mute=1,curve=1,pprop=0.99|				
			var pEnv=EnvGen.kr(Env.linen((1-pprop)*pdur/2,pprop*pdur,(1-pprop)*pdur/2,pamp).delay(pdelay),doneAction:2);
			var signal=SinOsc.ar(freq,mul:pEnv);
			var gEnv=EnvGen.kr(Env.linen(0,0,gdur,gamp,0-curve),doneAction:2)*mute;
			Out.ar(
				out,
				Pan2.ar(signal*gEnv,pan,1)
			)			
		}).store;
		SynthDef(\grainRexp, { |freq=100,gdur=1,gamp=1,pdelay=0,pdur=1,pamp=1,out=0,pan=0,mute=1,curve=1,pprop=0.99|				
			var pEnv=EnvGen.kr(Env.linen((1-pprop)*pdur/2,pprop*pdur,(1-pprop)*pdur/2,pamp).delay(pdelay),doneAction:2);
			var signal=SinOsc.ar(freq,mul:pEnv);
			var gEnv=EnvGen.kr(Env.linen(gdur,0,0,gamp,curve),doneAction:2)*mute;
			Out.ar(
				out,
				Pan2.ar(signal*gEnv,pan,1)
			)			
		}).store;
	}
}
