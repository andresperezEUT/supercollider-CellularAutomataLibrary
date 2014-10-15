// Andrés Pérez, 2011
// www.andresperezlopez.com

//see helpfiles for complete description of rule types

Generations2d : DiscreteType2d {
// is a outer-totalistic

	// k=2 always!
	*new { |r=1,neighborhoodType=\vonNeumann|
		^super.new.init(2,r,neighborhoodType);
	}
	
	setCount { |c=2|
		if (c<2)
			{k=2}
			{k=c};
	}
		
	// rule:
	// [ [S],[B],(C)]
	// [S] contains the numbers of the sum of neighbors for which a cell survives (1->1)
	// [B] contains the numbers of the sum of neighbors for which a cell borns    (0->1)
	// C (optional) sets the number of counts
	
	setRule {|r|
		rule=Array.newClear(k);
		k.do {|i| rule.put(i,r[i]) };
		if (r[2].isNil.not) {
			this.setCount(r[2])}			
	}
	
	applyRule {|cell,neighbors|

		var neighborsC=neighbors.collect { |v| 	if (v==1) {1} {0} };
		switch (cell)
			{0} 		 {	if (rule[1].includes(neighborsC.sum)) {^1} {^0} }
			{1} 		 {  if (rule[0].includes(neighborsC.sum)) {^1} {^this.applyCount(1)} }
			{k-1}	 { ^0 }
			{ ^this.applyCount(cell) };
	}	
	
	applyCount {|c|
		if (c==(k-1)) {^0} {^(c+1)}
	}
	
	printOn { arg stream;
			stream << "Two-Dimensional Generations Rule with k=" << k << ", r=" << r <<", rule=" << rule << ", neighborhood=" << neighborhoodType;
	}
		
}
