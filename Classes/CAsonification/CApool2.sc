// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional custom synthesis

CApool2 : CApool {

	var panMin;
	var panMax;
	var panDist;
	
	createSynth { |t,argX,argY|
		clock=t;	
		x=argX;
		y=argY;
		length=x*y;		
			
		instrument=\default;
		amp=0!length;
		panMin=(-1);
		panMax=1;
		panDist=\lin;
		pan=this.expandPan;	
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
			
			{\panMin}	{panMin=values;   valueList.put(parameterList.indexOf(\pan),this.expandPan)}
			{\panMax}	{panMax=values;   valueList.put(parameterList.indexOf(\pan),this.expandPan)}
			{\panDist}	{panDist=values;  valueList.put(parameterList.indexOf(\pan),this.expandPan)}
			
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
								valueList.put(index,this.expand(values,direction));
							} {
								"parameter not found".warn;
							}
						};
	}
	

	expandFreq {
		^this.expand(this.getArray(y,freqDist,freqMin,freqMax),\v);
	}
	
	expandPan {
		^this.expand(this.getArray(x,panDist,panMin,panMax),\h);
	}
	
	
	expand { |values,direction|
		var array;
		values=switch (values.class.name)
			{\Array2D}	{values.asArray}
			{\Array}	{ if (direction==\h) {
							values.dup(y).flat;
						} {
							values.do{|v| array=array++v.dup(x)};
							values=array.reverse;
						}
			}
			{\Integer}	{values!length}
			{\Float}	{values!length};
		^values;
	}
}
