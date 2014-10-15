// Andrés Pérez, 2011
// www.andresperezlopez.com

//creates arithmetic and geometric series

+ SequenceableCollection {

	*series2 { |size, start, end|
		var new = this.new(size);
		var step = (end-start)/(size-1);
		size.do {|i|
			new.add(start+(step*i));
		};
		^new;
		
	}
	
	*geom2 { |size,start,end|
		var new = this.new(size);
		var grow = (end/start)**(1/(size-1));
		size.do {
			new.add(start);
			start=start*grow;
		};
		^new;
	}
	
}
