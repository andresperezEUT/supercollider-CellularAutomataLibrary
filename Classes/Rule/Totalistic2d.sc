// Andrés Pérez, 2011
// www.andresperezlopez.com

//see helpfiles for complete description of rule types


Totalistic2d : DiscreteType2d {

	
	*new { |k=2,r=1,neighborhoodType=\moore|
		^super.new.init(k,r,neighborhoodType);
	}
	
	
	// rule:
	// rule[i] gives the output for the value i=(cell+neighbors).sum
	// rule.size is ((m+1)*(k-1))+1
	setRule {|r|
		rule=r;		
	}
	
	applyRule {|cell,neighbors|
		var all=neighbors.sum;
		^rule[all+cell];
		
	}
	
	printOn { arg stream;
			stream << "Two-Dimensional Totalistic Rule with k=" << k << ", r=" << r <<", rule=" << rule << ", neighborhood=" << neighborhoodType;
	}
		
}
