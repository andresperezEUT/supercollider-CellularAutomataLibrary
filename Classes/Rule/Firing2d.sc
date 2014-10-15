// Andrés Pérez, 2011
// www.andresperezlopez.com

//see helpfiles for complete description of rule types

Firing2d : DiscreteType2d {

	
	*new { |k=2,r=1,neighborhoodType=\moore|
		^super.new.init(k,r,neighborhoodType);
	}
	
	
	// rule:
	// rule[i] gives the output for the value numberOfCells > 0 (firing)
	// the rule table's dimenision is (m+1),k
	setRule {|r|
		rule=Array.newClear(k);
		k.do {|i| rule.put(i,r[i]) };		
	}
	
	applyRule {|cell,neighbors|
		^rule[cell][ neighbors.count({ |c| c>0 }) ];
	}
	
	printOn { arg stream;
			stream << "Two-Dimensional Firing Rule with k=" << k << ", r=" << r <<", rule=" << rule << ", neighborhood=" << neighborhoodType;
	}		
}
