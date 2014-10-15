// Andrés Pérez, 2011
// www.andresperezlopez.com

//one dimensional continous behaviour
//see helpfiles for complete description of rule types

Continuous1d : ContinuousType1d {

	*new { |ratio=1, v1=1, v2=0|
		^super.new.init(ratio,v1,v2);	
	}
	
	init { |ratio,v1,v2|
		k=2;
		r=ratio;
		m=(2*r)+1;
		a=v1;
		b=v2;	
	}
	
	applyRule {	|value|
		var res=(value.sum)/value.size;
		^this.applyFunction(res);
	}
	
	applyFunction { |x|
		var res=(a*x)+b;
		^mod(res,1);	
	}
	
	printOn { arg stream;
			stream << "One-Dimensional Continuous Rule with r=" << r << ", a=" << a << ", b=" << b;
	}

}
