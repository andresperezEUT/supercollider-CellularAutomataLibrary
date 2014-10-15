// Andrés Pérez, 2011
// www.andresperezlopez.com

//abstract class
//describes standard behaviour/methods for all rule types
//each rule type must implement its own behaviour

//see helpfiles for complete description of rule types

RuleType1d {

	var <k;
	var <r,<m;
	var <type;
	var <>name;

	// gives the next line to a given one by applying the rule
	// this method is called from CA.run 
	
	getNextLine { |line,border,indices,dict|
		var newline = this.extendLine(line,border);

		if (indices.isNil)
			// no indices specyfied (synchronous): apply to the whole line
			{^newline.collect {|item| this.applyRule(item,dict)}}
			// indices specyfied (asyncrhonous)
			{^newline.collect {|item,index| 
				if (indices.size==0)
				// only one index specyfied
					{ if (indices==index)
						// index specyfied: apply
						{this.applyRule(item,dict)}
						// index not specyfied: leave
						{line[index]}	
					}
				// many indices specifyed
					{ if (indices.includes(index))
						// index specyfied: apply
						{this.applyRule(item,dict)}
						// index not specyfied: leave
						{line[index]}			
					}
				}
			}
	}
	
	applyRule {}
	
	extendLine { |line,border|
		var first,last;	
		if (border.isNil)
			{first=line.rotate(r)[0..r-1]; last=line.rotate(0-r)[line.size-r..line.size-1]}
			{first=[border]; last=[border]};	
		
		^(first++line++last).slide(m,1).clump(m);	
	}
	
	isDiscrete {
			if (this.class.superclasses.includes(DiscreteType1d)) {^true} {^false};
	}
	isContinuous {
			if (this.class.superclasses.includes(ContinuousType1d)) {^true} {^false};
	}
}

DiscreteType1d : RuleType1d {

	var <n;
	var <rule,<ruleAsArray,<trt;

	//valid for standard and totalistic since n changes in each implementation
	numberOfRules {
			^(k**n).asFloat;
	}
	
	// Answer with all the possible neighborhoods in format Array of Arrays
	neighborhoods {
		^n.collect { |i| (n-1-i).asDigits(k,m)};	
	}
	
	// Answer with all the possible neighborhoods in format Array of Symbols
	neighborhoodsAsSymbols {
		^n.collect { |i| (n-1-i).asDigits(k,m).join.asSymbol};	
	}	
}

ContinuousType1d : RuleType1d {

	var <a,<b;
	
	applylFunction {}
}
