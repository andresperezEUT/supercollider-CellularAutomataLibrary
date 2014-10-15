// Andrés Pérez, 2011
// www.andresperezlopez.com

// Totalistic1d.sc
// Implements a totalistic rule

//see helpfiles for complete description of rule types

Totalistic1d : Standard1d {

	var <nW;
	
	init {|states=2, ratio=1|
		k=states;
		r=ratio;
		m=(2*r)+1;
		n=(m*(k-1))+1;
		nW=(k**m).asInteger;
		type=\Totalistic;
	}
	
	setDictionary {
		trt=();
		n.do{|i| trt.put(i.asSymbol, ruleAsArray[n-1-i])};
	}

	// Rule As Wolfram Array:
	// given a k-ary rule array in totalistic nomenclature, returns it in Wolfram's k-ary notation
	ruleAsWolframArray { arg ruleAsArray=Array.newFrom(this.ruleAsArray);
	try {
		var sum,arrayW;
		arrayW=Array.new(nW);
		nW.do {|i|
			sum=i.asDigits(k,m).sum;
			arrayW=arrayW.add(ruleAsArray.at(sum));
		};		
		^arrayW; 
	}
	{|error| "ruleAsWolframArray: given argument is not an Array".error; error.throw}
	}

	// Rule As Wolfram
	// given a decimal totalistic nomenclature rule number, returns it in Wolfram's decimal nomenclature
	ruleAsWolfram { arg rule=this.rule;
	try{
		^this.ruleAsWolframArray(rule.asDigits(k,n)).base2decimal(k).asInteger;
	}
	{|error| "setRule: given argument is not a SimpleNumber".error; error.throw}
	}
	
	// Answer with all the possible neighborhoods in format Array of Arrays
	neighborhoods {
		^nW.collect { |i| (nW-1-i).asDigits(k,m)};	
	}
	
	// Answer with all the possible neighborhoods in format Array of Symbols
	neighborhoodsAsSymbols {
		^nW.collect { |i| (nW-1-i).asDigits(k,m).join.asSymbol};	
	}

		//añadir arg dict=trt;
	applyRule { arg value,dict=trt;
		^dict.at(value.sum.asSymbol);
	}
	
	printOn { arg stream;
			stream << "One-Dimensional Totalistic Rule with k=" << k << ", r=" << r << ", rule=" << rule;
		}	
}
