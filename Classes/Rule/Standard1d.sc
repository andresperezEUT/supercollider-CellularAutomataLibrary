// Andrés Pérez, 2011
// www.andresperezlopez.com

// Standard1d.sc

// Implements a standard rule

//see helpfiles for complete description of rule types


Standard1d : DiscreteType1d {
	
	*new { |states=2,ratio=1|
		^super.new.init(states,ratio);
	}
	
	init {|states,ratio|
		k=states;
		r=ratio;
		m=(2*r)+1;
		n=(k**m).asInteger;
		type=\Standard;
	}
	
	setRule { |ru|	
	try {
		case
		// if the given number is too big, set it to the highest possible
		{ ru>=this.numberOfRules }
			{"setRule: given rule is too big".warn;
			("number of rules in "++this.type++" k"++k++"r"++r++"="++this.numberOfRules).postln;
			("rule "++ru++" set to " ++ (ru=(k-1).dup(n).base2decimal(k))++"\r---").postln;
			};
		rule=ru;
		ruleAsArray=rule.asDigits(k,n);
		this.setDictionary;
	}
	{|error| "setRule: given argument is not a SimpleNumber".error}
	}
	
	setRuleAsArray { |rA|
	try {
		case 
		// if the given array is too big, set it to the highest rule
		{ rA.size>n }
			{"setRule: given rule array too big".warn;
			("size of rule in "++this.type++" k"++k++"r"++r++"="++n++", given size="++rA.size).postln;
			("rule "++rA.join++" set to " ++ ((rA=(k-1).dup(n)).join)++"\r---").postln;
			}
		// if the given array is too small, fill in the left with 0
		{ rA.size<n }
			{ (n-rA.size).do{rA=rA.addFirst(0)} };
		// if there ir non k-ary digits, convert them to k-1			
		if (rA.indexOfGreaterThan(k-1).isNil==false)
			{"setRule: given rule array contains non k-ary elements".warn;
			("all non k-ary elements set to "++(k-1)++"\r---").postln;
			while ({rA.indexOfGreaterThan(k-1).isNil==false},
				{rA.put(rA.indexOfGreaterThan(k-1),k-1)}
			)			
			};
		ruleAsArray=rA;
		rule=ruleAsArray.base2decimal(k);
		this.setDictionary;
	}
	{|error| "setRuleAsArray: given argument is not an Array".error}
	}
	
	setDictionary {
		trt=();
		n.do{|i| trt.put(i.asDigits(k,m).join.asSymbol, ruleAsArray[n-1-i])};
	}
	
	//añadir arg dict=trt; quizás no hace falta!!!!
	applyRule { arg value,dict=trt;
		^dict.at(value.join.asSymbol);

	}
	
	printOn { arg stream;
			stream << "One-Dimensional Standard Rule with k=" << k << ", r=" << r << ", rule=" << rule;
	}	
}
