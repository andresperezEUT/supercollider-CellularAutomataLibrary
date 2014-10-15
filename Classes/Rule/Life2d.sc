// Andrés Pérez, 2011
// www.andresperezlopez.com

//see helpfiles for complete description of rule types

Life2d : DiscreteType2d {
// like generations2d, but without count
	
	// k=2 always!
	*new { |r=1,neighborhoodType=\moore|
		^super.new.init(2,r,neighborhoodType);
	}
		
	// rule:
	// [ [S],[B] ]
	// [S] contains the numbers of the sum of neighbors for which a cell survives (1->1)
	// [B] contains the numbers of the sum of neighbors for which a cell borns    (0->1)

	setRule {|r|
		rule=Array.newClear(k);
		k.do {|i| rule.put(i,r[i]) };		
	}
	
	applyRule {|cell,neighbors|
	
		if (cell==0) { if (rule[1].includes(neighbors.sum)) {^1} {^0} };
		if (cell==1) { if (rule[0].includes(neighbors.sum)) {^1} {^0} };
	}	
	
	printOn { arg stream;
			stream << "Two-Dimensional Life Rule with r=" << r <<", rule=" << rule << ", neighborhood=" << neighborhoodType;
	}
		
}
