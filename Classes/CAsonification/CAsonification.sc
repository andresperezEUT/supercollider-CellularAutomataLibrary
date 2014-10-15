// Andrés Pérez, 2011
// www.andresperezlopez.com

//main abstract sonification class

CAsonification {

	var <ssName;
	var synth;	
	var connexions,<conID;
	var conDict;
	var dict;
	var name;
	var length;
	var x,y;
	var clock;
	
	*new { |argssName|
		^super.new.init(argssName);
	}
	
	init { |argssName|
		ssName=argssName;
		conID=0;
		connexions=List.newClear(0);
		dict=Dictionary[\new->0,\diff->1,\birth->2];
		conDict=Dictionary[0->\synthPar,1->\ssName,2->\ssPar,3->\mul,4->\add,5->\expr,6->\direction];
	}

	synth {
	
	}
	
	free {
	
	}	
	
	addConnexion { |...args|
		var id=conID;
		connexions.add(args++(nil!4));
		conID=conID+1;
		^id;
	}
	
	setConnexion { |id...args|
		4.do { |i|
			connexions.at(id).put(i+3,args.at(i));
		}
	}
	
	getConnexion { |id|
		("connexion"++id++"- ").post;
		conDict.size.do {|i|
			if (connexions.at(id).isNil.not) {
				(conDict.at(i)++":"++connexions.at(id)[i]++", ").post;
			}
		};
		" ".postln;
	}
	
	removeConnexion { |id|
		connexions.put(id,nil);
	}
	
	update { |argssID,argvalues|
		connexions.do {|connexionParameters|
			var cp=connexionParameters;
			var synthParameter,ssID,ssParameter,mul,add,expr,direction;
			var values;
			
			if (cp.isNil.not) {
				synthParameter=cp.at(0);
				ssID=cp.at(1);
				ssParameter=cp.at(2);
				mul=cp.at(3);
				add=cp.at(4);
				expr=cp.at(5);
				direction=cp.at(6);

				if (ssID==argssID) {

					values=argvalues.at(dict.at(ssParameter));
					try{
					if (expr.isNil.not) {~x=values; values=expr.interpret};		
					if (values.class.name==\Array2D)
						{values=values.mul(mul).add(add)}
						{values=values*mul+add};				
					}
					{|e|"Expr not valid".warn;cp.put(5,"0")};	
				
					this.setParameter(synthParameter,values,direction);
				}
			}			
		}
	}	
	
	changeTempo { |tempo|
		clock.schedAbs(clock.beats.ceil,{clock.tempo_(tempo)});
	}
}
