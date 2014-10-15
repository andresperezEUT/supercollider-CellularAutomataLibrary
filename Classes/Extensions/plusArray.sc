// Andrés Pérez, 2011
// www.andresperezlopez.com

+ Array {

	//converts the number, expressed in the Array in the given base, to Integer decimal (base 2 i supossed)	
	base2decimal { |base=2|
		var r=0;
		var s=this.size;
		this.do {|item,index|
			r=r+(item*(base**(s-1-index)));	
		}
		^r.asFloat;	
	}
}
