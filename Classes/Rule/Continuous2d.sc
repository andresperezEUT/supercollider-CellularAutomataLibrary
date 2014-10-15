// Andrés Pérez, 2011
// www.andresperezlopez.com

//two dimensional continous behaviour
//see helpfiles for complete description of rule types

Continuous2d : ContinuousType2d {

	*new { |ratio=1, v1=1, v2=0,/*lattice=\square,*/neighborhoodType=\vonNeumann|
		^super.new.init(ratio,v1,v2,/*lattice,*/neighborhoodType);	
	}
	
	applyRule {	|cell, neighbors|
		var res=(neighbors.sum+cell)/(m+1);
		^this.applyFunction(res);
	}
	
	applyFunction { |x|
		var res=(a*x)+b;
		^mod(res,1);	
	}
	
	printOn { arg stream;
			stream << "Two-Dimensional Continuous Rule with r=" << r << ", a=" << a << ", b=" << b << ", neighborhood=" << neighborhoodType;
	}

}
