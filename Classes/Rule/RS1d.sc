// Andrés Pérez, 2011
// www.andresperezlopez.com

//	RS1d.sc
//	Creates a one-dimensional Rule Space for a given k and r
//  Features:
// - calculation of lambda parameter
// - searchs for behaviour based in lambda by tableWalk
// - performs rule transformations: complementary, negative, reflective 
// - lists preset rules
// - converts standard <-> totalistic rule nomenclatures
//
//	Variables:
//	k: number of states
//	r: ratio of neighborhood
//	m: total number of cells in the neighborhood
//	nW: size of the rule array for standard rules
//  nT: size of the rule array for totalistic rules
//  ruleW: current standard rule as decimal number;
//  ruleArrayW: current standard rule as k-ary array;
//  ruleT: current totalistic rule as decimal number;
//  ruleArrayT: current totalistic rule as k-ary array;
//  type: type of current rule (in order to perform calculations), 
//     \W=wolfram(standard), \T=totalisitc
//  sq: quiescent state in lambda calculations, set arbitrarily to 0

RS1d {
	var <k,<r,<m,<nW,<nT;
	var <ruleW,<ruleArrayW,<ruleT,<ruleArrayT;
	var <>type;
	var sq=0;

	*new { |states=2,ratio=1|
		^super.new.init(states,ratio);
	}
	
	init {|states=2, ratio=1|
		k=states;
		r=ratio;
		m=(2*r)+1;
		nW=(k**m).asInteger;
		nT=(m*(k-1))+1;
		type=\W;
		this.clearRules;
	}
	
	clearRules {
		ruleW=nil;
		ruleArrayW=Array.newClear(nW);
		ruleT=nil;
		ruleArrayT=Array.newClear(nT);
	}
	
	//reinicia la rule a 0!
	setArch { |states,ratio|
		this.init(states,ratio);
	}

	numberOfRules { arg t=type;
		if (t==\W) 
			{^(k**nW).asFloat}
			{^(k**nT).asFloat;}
	}
	
	// Answer with all the possible neighborhoods in format Array of Arrays
	neighborhoods {
		^nW.collect { |i| (nW-1-i).asDigits(k,m)};	
	}
	
	// Answer with all the possible neighborhoods in format Array of Symbols
	neighborhoodsAsSymbols {
		^nW.collect { |i| (nW-1-i).asDigits(k,m).join.asSymbol};	
	}	
		
	setRule {|rule|
		case 
			{type==\W} {this.setRuleW(rule)}
			{type==\T} {this.setRuleT(rule)}
	}
	
	setRuleW { |rule|	
		if ( rule>=this.numberOfRules ) {
		// if the given number is too big, set it to the highest possible		
			"setRule: given rule is too big".warn;
			ruleArrayW.fill(k-1);
			ruleW=ruleArrayW.base2decimal(k);
		}{
			ruleW=rule;
			ruleArrayW=rule.asDigits(k,nW);
			ruleT=nil;
			ruleArrayT=Array.newClear(nT);
		}
	}
	
	setRuleT { |rule|	
		if ( rule>=this.numberOfRules ) {
		// if the given number is too big, set it to the highest possible		
			"setRule: given rule is too big".warn;
			ruleArrayT.fill(k-1);
			ruleT=ruleArrayT.base2decimal(k);
		}{
			ruleT=rule;
			ruleArrayT=rule.asDigits(k,nT);
			ruleW=nil;
			ruleArrayW=Array.newClear(nW);
		}
	}

	setRuleArray {|ruleArray|
		if (ruleArray.indexOfGreaterThan(k-1).isNil==false) {
			("all non k-ary elements set to "++(k-1)++"\r---").warn;
			while ({ruleArray.indexOfGreaterThan(k-1).isNil==false},
				{ruleArray.put(ruleArray.indexOfGreaterThan(k-1),k-1)}
			)			
		};
		case 
			{type==\W} {this.setRuleArrayW(ruleArray)}
			{type==\T} {this.setRuleArrayT(ruleArray)}
	}

	setRuleArrayW { |ruleArray|
		ruleArrayW=ruleArray;
		ruleW=ruleArrayW.base2decimal(k);
		ruleT=nil;
		ruleArrayT=Array.newClear(nT);
	}
	
	setRuleArrayT { |ruleArray|
		ruleArrayT=ruleArray;
		ruleT=ruleArrayT.base2decimal(k);
		ruleW=nil;
		ruleArrayW=Array.newClear(nW);
	}
	
	// get ruleT in wolfram format
	getWolfram { 
		var sum,arrayW;
		nW.do {|i|
			sum=i.asDigits(k,m).sum;
			ruleArrayW.put(i,ruleArrayT.at(sum));
		};		
		ruleW=ruleArrayW.base2decimal(k);
	}
	
	// Rule is Totalistic:
	// (k2r1: 3)
	// sum contains the addition of the weights for each neighborhood 
	// (k2r1: [3,2,2,1,2,1,1,0] )
	// indices have each time the indices of sum that are equal to a number between 0 and max 
	// (k2r1: [7],[3,5,6],[1,2,4],[0] ) 
	ruleIsTot {
		var sum,indices;
		if (type==\T) {^true};
		
		sum=this.neighborhoods.collect {|c| c.sum };
		nT.do { |i|
			indices=sum.indicesOfEqual(i);
			if (indices.size>1) {
				(indices.size-1).do {|index| 
					if (ruleArrayW[indices[index]]!=ruleArrayW[indices[index+1]]) {^false};
				}
			}
		};
		^true;
	}

	// Rule As Totalistic Array
	// if ruleW is totalistic, get it in totalistic format
	getTotalistic { 
		var index,sumArray;
		if (this.ruleIsTot) {
			sumArray=nW.collect{|i| i.asDigits(k,m).sum}.reverse;
			nT.do {|i| 
				index=sumArray.indexOf(i);
				ruleArrayT.put(i,ruleArrayW[index]);
			};
			ruleArrayT=ruleArrayT.reverse;	
			ruleT=ruleArrayT.base2decimal(k);
		}{
			ruleT=nil;
			ruleArrayT=Array.newClear(nT);
			"rule is not totalistic".warn;
		}
	}
	
	// Activity Parameter Lambda as defined by Langton
	// (quiescent state arbitrarily set to 0)
	// lambda = ratio of non-quiescent transitions to the total number of transitions in the rule table
	// 			  0	  <-> 1-(1/k) <->   1
	//			order <->  chaos  <-> order 
	lambda {
		var nt;	
		if (ruleW.isNil) {this.getWolfram};
		nt=ruleArrayW.occurrencesOf(sq);
		^(nW-nt)/nW;
	}
	
	// Table Walk method by defined by Langton
	// search in the Rule Space for a rule with the specifyed lambda
	// able to use lambda and lambda2 methods
	// Table Walk will search for rules with lambda between lambda-tol and lambda+tol
	// returns in the format [rule as k-ary array, lambda founded]
	tableWalk { arg lambda, tol=0;
		var l,i,maxIter,states;
		this.setRuleArrayW(sq.dup(nW));
		l=this.lambda;
		i=0;
		maxIter=case {type==\W}{nW*2} {type==\T}{nT*2};
		states=(0..k-1);
		states.remove(sq);
		
		while ( { i<maxIter and: {l<(lambda-tol) or: { l>(lambda+tol )} }}, {
			if (l<(lambda-tol))				
				{ this.raiseLambda(states) }
				{ this.dropLambda(states)  };
			l=this.lambda;
			i=i+1;
		});
		
		if (i<maxIter) {
			ruleW=ruleArrayW.base2decimal(k);
			 if(type==\T){
			 	ruleT=ruleArrayT.base2decimal(k)
			 };
			 ^l
		} { 
			ruleW=nil; ruleArrayW=Array.newClear(nW);
			 if(type==\T){
			 	ruleT=nil; ruleArrayT=Array.newClear(nT);
			 };
			"No rule with such lambda".warn; ^nil; }			
	}
	
	// raises lambda by changing a random element of the transition rule to a non-quiescent state
	raiseLambda { |states|
		var index,item;
		if (type==\W) {
			index=ruleArrayW.indicesOfEqual(sq);
			item=states.choose;
			ruleArrayW.put(index.choose,item);
		};
		if (type==\T) {
			this.getTotalistic;
			index=ruleArrayT.indicesOfEqual(sq).choose;
			item=states.choose;
			ruleArrayT.put(index,item);
			this.getWolfram;			
		};
	}
	
	// drops lambda by changing a random element of the transition rule to a quiescent state
	dropLambda { |states|	
		var index,item;
		
		if (type==\W) {
			index=ruleArrayW.indicesOfEqual(states.choose);
			while ({index.isNil},
				{index=ruleArrayW.indicesOfEqual(states.choose)});
			item=sq;
			ruleArrayW.put(index[0],item);	//we take the first of the indices, it can be considered random	
		};
		if (type==\T) {
			this.getTotalistic;
			index=ruleArrayT.indicesOfEqual(states.choose);
			while ({index.isNil},
				{index=ruleArrayT.indicesOfEqual(states.choose)});
			item=sq;
			ruleArrayT.put(index[0],item);	//we take the first of the indices, it can be considered random	
			this.getWolfram;
		};
	}
	


	// Complementary Transformation Rc:
	getRc {
		var rule=case 
			{type==\W} {ruleArrayW}
			{type==\T} {ruleArrayT};
		var ruleC=Array.newClear(k-1); //k-1 different complementary
		(k-1).do { |i|
			ruleC.put(i,rule.collect{|v| mod((v+i+1),k)});
		}
		^ruleC;	
	}
	
	// Negative Transformation Rn:
	// performs the white/black transformation
	// 1. get the complementary rule
	// 2. reverse the binary digits (T0 <-> Tlast, T1 <-> Tlast-1...)
	
	getRn {
		var rule=case 
			{type==\W} {ruleArrayW}
			{type==\T} {ruleArrayT};
		var ruleN=Array.newClear(k-1); //k-1 different complementary
		var ruleC=this.getRc(rule);
		(k-1).do { |i|
			ruleN.put(i,ruleC[i].reverse);
		}
		^ruleN;
	}
	
	// Reflection Transformation Rr:
	// performs the left/right mirror transformation
	// swap rule binary digits in the position of its neighborhood's symmetric entry
	// neighborhoodsAsSymbols is used because indexOf work only with Symbols
	
	// example for k2r1:
	// pos=   		[ 0,  1,  2,  3,  4,  5,  6,  7 ]
	// neigh= 		[111,110,101,100,011,010,001,000]
	// mirror=		[111,011,101,001,110,010,100,000]
	// ruleArrayW=  [ T7, T6, T5, T4, T3, T2, T1, T0]
	// 8.do{ ... }		
	// -111 is not in the list; 111==111 -> ...
	// -011 is not in the list; 110!=011 -> rule.swap(1,4): T6<->T3 - add 110
	// -101 is not in the list; 101==101 -> ...
	// -001 is not in the list; 100!=001 -> rule.swap(3,6): T4<->T1 - add 100
	// -110 is in the list				 -> ...
	// -010 is not in the list; 010==010 -> ...
	// -101 is in the list				 -> ...
	// -000 is not in the list; 000==000 -> ...
	// ^rule=[ T7, T3, T5, T1, T6, T2, T4, T0]
	
	getRr {
		var rule=case 
			{type==\W} { ruleArrayW}
			{type==\T} {^ruleArrayT};			
		var neigh,mirror,done;
		
		if (this.ruleIsTot) {^rule};
		neigh=this.neighborhoodsAsSymbols;
		mirror=this.neighborhoods.collect{|c| c.reverse.join.asSymbol};
		done=List.new;
		
		nW.do{ |i| 
			if (done.indexOf(mirror[i]).isNil and: {mirror[i]!=neigh[i]}) {
				rule.swap(i,mirror.indexOf(neigh[i]));
				done.add(neigh[i]);
			};	
		}
		^rule;
	}


	getLiClassification { |type|
		var null=\null++Array.with(0,8,32,40,128,136,160,168);
		var fixedPoint=\fixedPoint++Array.with(2,4,10,12,13,24,34,36,42,44,46,56,57,58,72,76,77,78,104,130,132,138,140,152,162,164,170,172,184,200,204,232);
		var twoCycle=\twoCycle++Array.with(1,3,5,6,7,9,11,14,15,19,23,25,27,28,29,33,35,37,38,43,50,51,74,108,134,142,156,178);
		var periodic=\periodic++Array.with(26,41,73,131,133,154);
		var complex=\complex++Array.with(54,137);
		var chaotic=\chaotic++Array.with(18,22,30,45,60,90,105,106,129,146,150,161);
		
		^switch(type)
			{\null}			{null}
			{\fixedPoint}	{fixedPoint}
			{\twoCycle}		{twoCycle}
			{\periodic}		{periodic}
			{\complex}		{complex}
			{\chaotic}		{chaotic}
			{nil}			{null++fixedPoint++twoCycle++periodic++complex++chaotic};
	}
	

}
