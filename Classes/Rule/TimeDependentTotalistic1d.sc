// Andrés Pérez, 2011
// www.andresperezlopez.com

// TimeDependentTotalistic1d.sc
// Implements a time dependent totalistic rule

//see helpfiles for complete description of rule types

TimeDependentTotalistic1d : TimeDependent1d {

	var <nW;
	
	init { |states=2, ratio=1, numRules=2, change=\random|
		super.init(states,ratio,numRules,change);
		nW=n;
		n=(m*(k-1))+1;
		type=\TimeDependentTotalistic;
	}
	
	setDictionary { |index|
		trt[index]=();
		n.do{|i| trt[index].put(i.asSymbol, ruleAsArray[index][n-1-i])};
	}
	
	applyRule {|value,dict|
		^dict.at(value.sum.asSymbol);
	}
	
	printOn { arg stream;
			stream << "One-Dimensional Time-Dependent Totalistic Rule with k=" << k << ", r=" << r << ", rule=" << rule;
	}
	
}
