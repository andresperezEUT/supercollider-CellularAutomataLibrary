// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional custom synthesis

CApool1 : CApool {
	
	
	createSynth { |t,l|
		clock=t;	
		length=l;		
			
		instrument=\default;
		amp=0!length;
		pan=0!length;
		freqMin=100;
		freqMax=1000;
		freqDist=\lin;
		freq=this.expandFreq;			
		out=0!length;

		parameterList=List.newUsing([\amp,\pan,\freq,\out]);
		valueList=List.newUsing([amp,pan,freq,out]);
		synths=Array.newClear(length);
	}


	
	setParameter { |synthParameter,values,direction|	
		var index;
		switch (synthParameter)
			{\tempo}		{this.changeTempo(values)}
			{\instrument}	{this.setInstrument(values)}
			{\mute}			{mute=true}
			{\unmute}		{mute=false}
			
			{\freqMin}	{freqMin=values;  valueList.put(parameterList.indexOf(\freq),this.expandFreq)}
			{\freqMax}	{freqMax=values;  valueList.put(parameterList.indexOf(\freq),this.expandFreq)}
			{\freqDist}	{freqDist=values; valueList.put(parameterList.indexOf(\freq),this.expandFreq)}
			
			{\addPar}	{parameterList.add(values); valueList.add(0!length)}
			{\getPar}	{this.getParameters}	
			{\remPar}	{	index=parameterList.indexOf(values);
							if (index.isNil.not) {
								parameterList.removeAt(index);
								valueList.removeAt(index);
							} {
								"parameter not found".warn;
							}
						}		
			
			/*else*/	{
							index=parameterList.indexOf(synthParameter);
							if (index.isNil.not) {
								valueList.put(index,this.expand(values));
							} {
								"parameter not found".warn;
							}
						};
	}
	
	

	expandFreq {
		^this.getArray(length,freqDist,freqMin,freqMax);
	}
	
	
	expand { |values|
		if (values.class.superclass.name==\SimpleNumber) {values=values!length};
		^values;
	}
	
}
