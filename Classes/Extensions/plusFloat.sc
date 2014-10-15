// Andrés Pérez, 2011
// www.andresperezlopez.com

// base conversion

+ Float {


	asDigits { arg base = 2, numDigits = 8;
		var number=this;
		var out=List.new;
		while({number>=base},
			{	out.add(mod(number,base));
				number=(number/base).floor;		
			});
		out.add(number.floor);
		case
			{out.size<numDigits} 
				{(numDigits-out.size).do {out.add(0)}}
			{out.size>numDigits} 
				{	("asDigits: numDigits less than required - took LSBs").warn;
					("required="++out.size++", numDigits="++numDigits).postln;
					out=out[0..numDigits-1] };
		^out.reverse.asArray;
	}
	
	asBinaryDigits { arg numDigits = 8;
		^this.asDigits(2,numDigits);
	}
	
	

}
