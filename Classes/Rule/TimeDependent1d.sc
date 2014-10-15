// Andrés Pérez, 2011
// www.andresperezlopez.com

// TimeDependent1d.sc
// Implements a time dependent rule

// Variables:
// t: number of different rules
// changeType: \random - 	each line applies a different rule choosen by random
//				\periodic -	changes the rule each certain period
// period: period of change for periodic changes
// prob: probabilities of the different rules as vector, for random changes

//see helpfiles for complete description of rule types

TimeDependent1d : DiscreteType1d {

	var <t,<changeType,<period,<prob;
	var rIndex=0,getNextIndex=0,dictIndex=0;
	
	*new { |states=2,ratio=1,numRules=2,change=\random|
		^super.new.init(states,ratio,numRules,change);
	}
		
	init {|states, ratio, numRules, change|
		k=states;
		r=ratio;
		m=(2*r)+1;
		n=(k**m).asInteger;
		t=numRules;
		changeType=change;
		period=10;
		prob=1.dup(t).normalizeSum;
		rule=Array.newClear(t);
		ruleAsArray=Array.newClear(t);
		trt=Array.newClear(t);
		type=\TimeDependent;
	}
	
	setChangeType {|change|
		changeType=change;
	}
	
	setPeriod {|p|
		period=p;
	}

	setProbability {arg pr=1.dup(t).normalizeSum;
		if (pr.size!=t)
			{("in setProbability: prob.size must be equal to t="++t++"\rProbability set to "++prob++"\r---").warn}
			{prob=pr}
	}
	
	addRule { |ru|
	try{
		var index,rA;
		case
		{ ru>=this.numberOfRules }
			{"setRule: given rule is too big".warn;
			("number of rules in "++this.type++" k"++k++"r"++r++"="++this.numberOfRules).postln;
			("rule "++ru++" set to " ++ (ru=(k-1).dup(n).base2decimal(k))++"\r---").postln;
			};
		index=mod(rIndex,t);
		rA=ru.asDigits(k,n);
		rule.put(index,ru);
		ruleAsArray.put(index,rA);	
		this.setDictionary(index);
		rIndex=rIndex+1;
	}
	{|error| "setRule: given argument is not a SimpleNumber".error; error.throw}
	}
	
	addRuleAsArray { |rA|
	try {
		var index,ru;
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
		index=mod(rIndex,t);
		ru=rA.base2decimal(k);
		rule.put(index,ru);
		ruleAsArray.put(index,rA);		
		this.setDictionary(index);	
		rIndex=rIndex+1;
	}
	{|error| "setRuleAsArray: given argument is not an Array".error; error.throw}
	}
	
	setDictionary { |index|
		trt[index]=();	
		n.do{|i| trt[index].put(i.asDigits(k,m).join.asSymbol, ruleAsArray[index][n-1-i])};
	}
	
	
	resetCount {
		getNextIndex=0;
		dictIndex=0;
	}
	
	updateIndex {
		getNextIndex=getNextIndex+1;
		dictIndex=mod((getNextIndex/period).floor,t);
	}
	
	
	getNextLine { |line,border,indices|	
		var dict;		
		switch (changeType,
			\random, 	{dict=trt.wchoose(prob)},
			\periodic,	{dict=trt.at(dictIndex)},
			{"TimeDependent1d - Incorrect Change Type".warn;MethodError.new.throw}
		);
		
		this.updateIndex;	
		^super.getNextLine(line,border,indices,dict);
	}
		
	applyRule {|value,dict|
		^dict.at(value.join.asSymbol);
	}	

	printOn { arg stream;
			stream << "One-Dimensional Time-Dependent Standard Rule with k=" << k << ", r=" << r << ", rule=" << rule;
	}	

}
