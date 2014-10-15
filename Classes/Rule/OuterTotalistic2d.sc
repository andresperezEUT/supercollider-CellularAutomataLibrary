// Andrés Pérez, 2011
// www.andresperezlopez.com

//see helpfiles for complete description of rule types

OuterTotalistic2d : DiscreteType2d {

	
	*new { |k=2,r=1,neighborhoodType=\moore|
		^super.new.init(k,r,neighborhoodType);
	}
	
	
	// rule:
	// rule[i] gives the rule array for each i=value of the center cell
	// rule[i][j] gives the oputput for a cell i and a neighborhood sum j	
	// each sub-array has a size of (m+1)*(k-1), total size= this*k;
	setRule {|r|
		rule=Array.newClear(k);
		k.do {|i| rule.put(i,r[i]) };			
	}
	
	applyRule {|cell,neighbors|
		^rule[cell][neighbors.sum];
	}
	
	printOn { arg stream;
			stream << "Two-Dimensional OuterTotalistic Rule with k=" << k << ", r=" << r <<", rule=" << rule << ", neighborhood=" << neighborhoodType;
	}
		
}
