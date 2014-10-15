// Andrés Pérez, 2011
// www.andresperezlopez.com

// main cellular automata class

CA {

	var <ruleType;
	var <>name;
	var <>ruleName;
	
	classvar rIndex=0;
	classvar rList;
	classvar rDict;
	
	classvar caIndex=0;
	classvar caList;
	classvar caDict;
	
	classvar <ssIndex=0;
	classvar ssList;
	classvar ssDict;	

	classvar win, winFront;
	classvar <clocks;	
	classvar clocksIndex=0;
	classvar clocksID;
	classvar seedState;
	classvar pause;
	classvar end;
	classvar delta=0.001;
	
	classvar mIndex=0;
	classvar <mList;
	classvar mDict;
	classvar seqIndex=0, keyIndex=0, subIndex=0, addIndex=0, grainIndex=0, poolIndex=0;
	
// INITIALIZATION	

	*initList { |v|
		if (v.isNil) {v=List.newClear(0)};
		^v;
	}
	
	*initDict { |v|
		if (v.isNil) {v=Dictionary.new(0)};
		^v;
	}
	
	*clearAll {
		rIndex=caIndex=ssIndex=mIndex=clocksIndex=0;
		seqIndex=keyIndex=subIndex=addIndex=grainIndex=poolIndex=0;
		mList.do {|v,i| mList.at(i).free};
		clocks.do {|v,i| clocks.at(i).clear};
		rList=caList=ssList=mList=clocks=clocksID=nil;
		rDict=caDict=ssDict=mDict=nil;
		pause=end=seedState=win=winFront=nil;
		^"Clear all";
	}
	
	*getRuleList {
		var key,rule;
		rIndex.do { |i|
			key=rDict.findKeyForValue(i);
			rule=rList.at(i);
			[key,rule].postln;
		}
		^"Rule List";
	}
	
	*getCAList {
		var key,ca,rule;
		caIndex.do { |i|
			key=caDict.findKeyForValue(i);
			ca=caList.at(i);
			rule=caList.at(i).ruleName;
			[key,ca,rule].postln;
		}
		^"CA List";
	}
	
	*getSSList {
		var key,ss,ca,clock;
		ssIndex.do { |i|
			key=ssDict.findKeyForValue(i);
			ss=ssList.at(i);
			ca=ssList.at(i).caName;
			clock=clocksID.at(i);
			[key,ss,ca,clock].postln;			
		}
		^"SS List";
	}
	
	*getSonificationList {
		var key,m,ss;
		mIndex.do { |i|
			key=mDict.findKeyForValue(i);
			m=mList.at(i);
			ss=mList.at(i).ssName;
			[key,m,ss].postln;			
		}
		^"Sonification List";
	}
	
// RULE FUNCTIONALITY
	
	// create rule
	
	*newRT1 { |ruleType,k=2,r=1,rule,ruleAsArray,changeType=\random,period=5,probability,v1=1,v2=0,ruleName|
		var rT,name;
		switch (ruleType)
			{\standard}		{
				if (rule.isNil.not) {
					switch(rule.class.name)
						{\Integer}	{rT=Standard1d.new(k,r).setRule(rule)}
						{\Array}	{rT=TimeDependent1d.new(k,r,rule.size,changeType).setPeriod(period);
									 rule.do {|r| rT.addRule(r)};
									 if (probability.isNil) {rT.setProbability(1.dup(rule.size).normalizeSum)} {rT.setProbability(probability)}};
				} {
					switch(ruleAsArray.at(0).class.name)
						{\Integer}	{rT=Standard1d.new(k,r).setRuleAsArray(ruleAsArray)}
						{\Array}	{rT=TimeDependent1d.new(k,r,ruleAsArray.size,changeType).setPeriod(period);
									 ruleAsArray.do {|r| rT.addRuleAsArray(r)};
									 if (probability.isNil) {rT.setProbability(1.dup(ruleAsArray.size).normalizeSum)} {rT.setProbability(probability)}};
				};
			}
			{\totalistic}	{
				if (rule.isNil.not) {
					switch(rule.class.name)
						{\Integer}	{rT=Totalistic1d.new(k,r).setRule(rule)}
						{\Array}	{rT=TimeDependentTotalistic1d.new(k,r,rule.size).setChangeType(changeType).setPeriod(period).setProbability(probability);
									 rule.do {|r| rT.addRule(r)}};
				} {
					switch(ruleAsArray.at(0).class.name)
						{\Integer}	{rT=Totalistic1d.new(k,r).setRuleAsArray(ruleAsArray)}
						{\Array}	{rT=TimeDependentTotalistic1d.new(k,r,ruleAsArray.size).setChangeType(changeType).setPeriod(period).setProbability(probability);
									 ruleAsArray.do {|r| rT.addRuleAsArray(r)}};
				};
			}
			{\continuous}	{
				rT=Continuous1d.new(r,v1,v2);
			};			
		if (ruleName.isNil.not)	{
			name=ruleName.asSymbol;
		} {
			name=("r"++rIndex).asSymbol;
		};
		rT.name_(name);
		rDict=this.initDict(rDict);
		rDict.add( name -> rIndex );
		rList=this.initList(rList);
		rList.add(rT);
		rIndex=rIndex+1;
		(name++" -> "++rT).postln;
		^rT;
		
	}
		
	*newRT1fromPreset { |preset,ruleName|
		var presets=Preset1d.allSubclasses;
		var index,class;
		if (preset.isNil.not) {
			index=presets.collect{|p| p.asSymbol}.indexOf(preset);
		} {
			index=(0..presets.size-1).choose;
		};
		class=presets.at(index).postln;
		^this.newRT1(class.type,class.k,class.r,ruleAsArray:class.rule,ruleName:ruleName);
	}
	
	
	*newRT2 { |ruleType,k=2,r=1,rule,neighborhoodType=\moore,v1=1,v2=0,ruleName|
		var rT,name;
		rT=switch (ruleType)
			{\totalistic}		{Totalistic2d.new(k,r,neighborhoodType).setRule(rule)}
			{\outerTotalistic}	{OuterTotalistic2d.new(k,r,neighborhoodType).setRule(rule)}
			{\life}				{Life2d.new(r,neighborhoodType).setRule(rule)}
			{\generations}		{Generations2d.new(r,neighborhoodType).setRule(rule)}
			{\firing}			{Firing2d.new(k,r,neighborhoodType).setRule(rule)}
			{\margolus}			{Margolus2d.new.setRule(rule)}
			{\continuous}		{Continuous2d.new(r,v1,v2,neighborhoodType)};
			
		if (ruleName.isNil.not)	{
			name=ruleName.asSymbol;
		} {
			name=("r"++rIndex).asSymbol;
		};
		rT.name_(name);
		rDict=this.initDict(rDict);
		rDict.add( name -> rIndex );
		rList=this.initList(rList);
		rList.add(rT);
		rIndex=rIndex+1;
		(name++" -> "++rT).postln;
		^rT;
		
	}
	
	
	*newRT2fromPreset { |preset,ruleType,neighborhoodType=\moore,ruleName|
		var presets=Preset2d.allSubclasses;
		var index,class;
		if (preset.isNil.not) {
			index=presets.collect{|p| p.asSymbol}.indexOf(preset);
		} {
			if (ruleType.isNil.not) {
				presets=switch (ruleType)
					{\firing}		{Preset2dF.subclasses}
					{\life}			{Preset2dL.subclasses}
					{\generations}	{Preset2dG.subclasses}
					{\margolus}		{Preset2dM.subclasses};		
				index=(0..presets.size-1).choose;	
			} {
				index=(4..presets.size-1).choose;
			}
		};
		class=presets.at(index).postln;
		^this.newRT2(class.type,class.k,class.r,class.rule,neighborhoodType,ruleName:ruleName);
	}

	
	// list preset rules
	
	*listPresets{ |dim,ruleType,character=nil|
		var presets,out;
		switch (dim)
			{1}	{
				presets=Preset1d.subclasses;				
			}
			{2}	{
			presets=switch (ruleType)
				{nil}			{Preset2dF.subclasses++Preset2dL.subclasses++Preset2dG.subclasses++Preset2dM.subclasses}
				{\firing}		{Preset2dF.subclasses}
				{\life}			{Preset2dL.subclasses}
				{\generations}	{Preset2dG.subclasses}
				{\margolus}		{Preset2dM.subclasses};		
			};
			
		presets.do {|p| if (character.isNil or:{character==p.character}) {p.name.postln} };
		^"---";
	}
	
	*listPresets1 {
		^this.listPresets(1);
	}
	
	*listPresets2 { |ruleType,character=nil|
		^this.listPresets(2,ruleType,character);
	}
	
	
	// Rule Space 1d funcionality - from RS1d.sc 
	
	*numberOfRules { |ruleName| 
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		^RS1d.new(rule.k,rule.r).numberOfRules;
	}
	
	*setRule { |ruleName,rule| 
		var rID=rDict.at(ruleName);
		^rList.at(rID).setRule(rule);
	}
	
	*setRuleAsArray { |ruleName,ruleArray| 
		var rID=rDict.at(ruleName);
		^rList.at(rID).setRuleAsArray(ruleArray);	
	}
	
	*neighborhoods { |ruleName| 
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		^RS1d.new(rule.k,rule.r).neighborhoodsAsSymbols;
	}
	
	*lambda { |ruleName| 
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		var r=RS1d.new(rule.k,rule.r);
		switch(rule.class.name)
			{\Standard1d}	{r.setRuleW(rule.rule)}
			{\Totalistic1d}	{r.setRuleT(rule.rule)};
		^r.lambda;
	}
	
	*tableWalk { |ruleName,lambda,tol=0|
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		var r=RS1d.new(rule.k,rule.r);
		if (rule.class.name==\Totalistic1d)	{r.type_(\T)};
		r.tableWalk(lambda,tol);
		switch(rule.class.name)
			{\Standard1d}	{^r.ruleW}
			{\Totalistic1d}	{^r.ruleT};
	}
	
	*getRc { |ruleName| 
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		var r=RS1d.new(rule.k,rule.r);
		var out;		
		switch(rule.class.name)
			{\Standard1d}	{r.setRuleW(rule.rule)}
			{\Totalistic1d}	{r.type_(\T).setRuleT(rule.rule)};
		out=r.getRc;
		if (rule.k==2) {out=out.flat};
		^out;
	}
	
	*getRn { |ruleName| 
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		var r=RS1d.new(rule.k,rule.r);
		var out;		
		switch(rule.class.name)
			{\Standard1d}	{r.setRuleW(rule.rule)}
			{\Totalistic1d}	{r.type_(\T).setRuleT(rule.rule)};
		out=r.getRn;
		if (rule.k==2) {out=out.flat};
		^out;
	}
	
	*getRr { |ruleName| 
		var rID=rDict.at(ruleName);
		var rule=rList.at(rID);
		var r=RS1d.new(rule.k,rule.r);		
		switch(rule.class.name)
			{\Standard1d}	{r.setRuleW(rule.rule)}
			{\Totalistic1d}	{r.type_(\T).setRuleT(rule.rule)};
		^r.getRr;
	}
	
	*getLiClassification { |type|
		^RS1d.new.getLiClassification(type);
	}
	
// CELLULAR AUTOMATA FUNCTIONALITY

	// create cellular automata	
	
	*newCA1 { |ruleName,caName|
		var rID;
		if (ruleName.class.name==\Symbol) {
			rID=rDict.at(ruleName);
			^this.newCA(1,rList.at(rID),caName);
		} {
			^this.newCA(1,ruleName,caName);
		}
	}
	
	*newCA2 { |ruleName,caName|
		var rID;
		if (ruleName.class.name==\Symbol) {
			rID=rDict.at(ruleName);
			^this.newCA(2,rList.at(rID),caName);
		} {
			^this.newCA(2,ruleName,caName);
		}
	}
	
	*newCA { |dim,ruleType,argName|
		var name;
		var ca=switch (dim)
			{1}	{CA1.new(ruleType)}
			{2}	{CA2.new(ruleType)};	
		caList=this.initList(caList);
		caList.add(ca);
		if (argName.isNil.not)	{
			name=argName.asSymbol;
		} {
			name=("ca"++caIndex).asSymbol;
		};
		ca.name_(name);
		caDict=this.initDict(caDict);
		caDict.add( name -> caIndex );
		caIndex=caIndex+1;
		(name++" -> "++ruleType).postln;
		^ca;
	}
	
	// create a Space State	

	*runCA { |caName,d=10,x=10,y=10,b,seed=\single,seedProb,update=\synchronous,updateProb=0.5,generations=10,clock|
		var caID,ca;
		var ss=ssIndex;
		if (caName.class.name==\Symbol) {
			caID=caDict.at(caName);
			ca=caList.at(caID);
		} {
			ca=caName;
		};
		switch (ca.class.name)
			{\CA1}	{ca.run(d,b,seed,seedProb,update,updateProb,generations,clock)}
			{\CA2}	{ca.run(x,y,b,seed,seedProb,clock)};
		^("run "++caName++" -> ss"++ss);
	}
	
	// private
	
	setID { |dimension,generations,isDiscrete,clock|
		var out;
		out=ssIndex;
		if (ssList.isNil) {ssList=List.newClear(0)};
		if (dimension==1) {
			ssList.add(SS1.new(ssIndex,seedState.at(ssIndex),generations,isDiscrete,clock,name));
		} {
			ssList.add(SS2.new(ssIndex,seedState.at(ssIndex),isDiscrete,clock,name));
		};		
		if (ssDict.isNil) {ssDict=Dictionary.new(0)};
		ssDict.add(("ss"++ssIndex).asSymbol -> ssIndex);
		ssIndex=ssIndex+1;
		^out;		
	}
	
	newClock { |clockID|
		var t;
		var id;
		if (clocks.isNil) {clocks=List.newClear(0)};
		if (clocksID.isNil) {clocksID=List.newClear(0)};
		if (clockID.isNil) {
			t=TempoClock.new;
			id=clocksIndex;
			
		} {
			t=clocks.at(clockID);
			id=clockID;
		};
		clocks.add(t);
		clocksID.add(id);
		clocksIndex=clocksIndex+1;
		^t;
	}
	
	
	updateSS { |ssID,values|
		ssList.at(ssID).update(values);		
		mList.do {|v| v.update(ssDict.findKeyForValue(ssID),values)};
	}
	
	*updateFromSS {|ssID,values|
		mList.do {|v| v.update(ssDict.findKeyForValue(ssID),values)};
	}
	
// STATE SPACE FUNCTIONALITY

	// control state space
	
	*getNumberOfSteps { |ssName|
		var ssID=ssDict.at(ssName);
		^ssList.at(ssID).n;
	}
	
	*pause { |ssName|
		var ssID=ssDict.at(ssName);
		pause.put(ssID,true);
		ssList.at(ssID).pause;
		^"Pause ss"++ssID;
	}
	
	*play { |ssName|
		var ssID=ssDict.at(ssName);
		pause.put(ssID,false);
		^"Play ss"++ssID;
	}
	
	*replay { |ssName,initStep=0,endStep|
		var ssID=ssDict.at(ssName);
		pause.put(ssID,true);
		if (endStep.isNil) {endStep=ssList.at(ssID).n-1};
		ssList.at(ssID).replay(initStep,endStep);
		^"Replay ss"++ssID;
	}
	
	*end { |ssName|
		var ssID=ssDict.at(ssName);
		end.put(ssID,true);
		^"End ss"++ssID;
	}
	
	*changeTempo {|ssName,tempo|
		var ssID=ssDict.at(ssName);
		var clock=clocks.at(ssID);
		clock.schedAbs(clock.beats.ceil,{clock.tempo_(tempo)});
		^"Tempo of clock in ss"++ssID++" changed to "++tempo;
	}

	*controlGUI { |ssName|
		var ssID=ssDict.at(ssName);
		win.at(ssID).front;
		if (winFront.at(ssID))
			{win.at(ssID).minimize}
			{win.at(ssID).unminimize};
		winFront.put(ssID,winFront.at(ssID).not);
	}
	
	// control visualization
	
	*viewSS { |ssName|
		var ssID=ssDict.at(ssName);
		var ss=ssList.at(ssID);
		if (ss.view.isNil) {
			ss.setView(ssID);
		};
		ss.view.ss;
	}
	
	*viewHSS { |ssName|
		var ssID=ssDict.at(ssName);
		var ss=ssList.at(ssID);
		if (ss.view.isNil) {
			ss.setView(ssID);
		};
		ss.view.hss;
	}
	
	*setGrid { |ssName|
		var ssID=ssDict.at(ssName);
		var ss=ssList.at(ssID);
		if (ss.view.isNil) {
			ss.setView(ssID);
		};
		ss.view.setGrid;
	}
	
	*setBirths { |ssName|
		var ssID=ssDict.at(ssName);
		var ss=ssList.at(ssID);
		if (ss.view.isNil) {
			ss.setView(ssID);
		};
		ss.view.setBirths;
	}
	
	*visualGUI { |ssName|
		var win,ssID,ss;
		ssID=ssDict.at(ssName);
		ss=ssList.at(ssID);
		ss.setView(ssID);			
		win= Window("View Control SS"++ssID, Rect(100, 500, 400, 120)).front.alwaysOnTop_(true).userCanClose_(true);
		Button.new(win,Rect(50,10,30,30)).states_([["grid",Color.white,Color.black]]).action_({ ss.view.setGrid });
		Button.new(win,Rect(100,10,30,30)).states_([["births",Color.white,Color.black]]).action_({ ss.view.setBirths });
		Button.new(win,Rect(100,50,30,30)).states_([["ss",Color.white,Color.black]]).action_({ ss.view.ss });
		if (ss.class.name==\SS1) {
			Button.new(win,Rect(100,100,30,30)).states_([["hss",Color.white,Color.black]]).action_({ ss.view.hss });
		};
		^"View Control ss"++ssID;	
	}
	
	// private 
	
	createGui { |ssID|
		if (win.isNil) {win=List.newClear(0)};
		win.add( Window("SS"++ssID, Rect(100, 500, 400, 120)).alwaysOnTop_(true).userCanClose_(true));
		Button.new(win.at(ssID),Rect(50,10,30,30)).states_([["pause",Color.white,Color.black]]).action_({ this.class.pause(ssDict.findKeyForValue(ssID)) });
		Button.new(win.at(ssID),Rect(100,10,30,30)).states_([["play",Color.white,Color.black]]).action_({ this.class.play(ssDict.findKeyForValue(ssID)) });
		Button.new(win.at(ssID),Rect(125,50,30,30)).states_([["X",Color.white,Color.black]]).action_({ this.class.end(ssDict.findKeyForValue(ssID)); win.at(ssID).minimize; });
	
	}
	
	
// SONIFICATION FUNCTIONALITY

	// connexion State Space - Sonification
	
	*connect { |synthName,synthParameter,ssName,ssParameter,mul=1,add=0,expr,direction|
		var mID=mDict.at(synthName);
		var synth=mList.at(mID);
		var conID=synth.addConnexion(synthParameter,ssName,ssParameter);
		synth.setConnexion(conID,mul,add,expr,direction);
		synth.getConnexion(conID);
		^conID;			
	}
	
	*setConnexion { |synthName,id,mul=1,add=0,expr,direction|
		var mID=mDict.at(synthName);
		var synth=mList.at(mID);
		synth.setConnexion(id,mul,add,expr,direction);
		synth.getConnexion(id);
	}
	
	*getConnexion { |synthName,id|
		var mID=mDict.at(synthName);
		var synth=mList.at(mID);
		if (id.isNil) {
			synth.conID.do {|i| synth.getConnexion(i)}
		} {
			synth.getConnexion(id);
		}
	}
	
	*disconnect { |synthName,id|
		var mID=mDict.at(synthName);
		var synth=mList.at(mID);
		if (id.isNil) {
			synth.conID.do {|i| synth.removeConnexion(i)}
		} {
			synth.removeConnexion(id);
		}
	}
	
	// control Sonification
	
	*setSound { |synthName,synthParameter,values,direction|
		var mID=mDict.at(synthName);
		var synth=mList.at(mID);
		synth.setParameter(synthParameter,values,direction);
	}
	
	*synth { |synthName|
		var mID=mDict.at(synthName);
		var synth=mList.at(mID);
		synth.synth;
	}
	
	*free { |synthName|
		var mID=mDict.at(synthName);
		if (mID.isNil.not) {
			mList.at(mID).free;
			^synthName++" free";
		} {
			^synthName++" not found";
		}
	}

	// create Sonification classes
	
	*newSonification { |sonificationType,ssName,synthName,dim,direction|
		var name;
		var ssID=ssDict.at(ssName);
		var ss=ssList.at(ssID);
		var clock=clocks.at(ssID);	
		var x,y,length;
		var class;
		var index;
		
		switch (ss.class.name)
			{\SS1}	{
				length=ssList.at(ssID).length;	
				dim=1;	
			}
			{\SS2}	{
				if (dim==1) {
					switch (direction)
						{\v}	{length=ssList.at(ssID).y}
						{\h}	{length=ssList.at(ssID).x};
				} {
					dim=2;
					x=ssList.at(ssID).x;
					y=ssList.at(ssID).y;
				}
			};			
		switch (sonificationType)
			{\sub}		{ 
				index=subIndex;
				subIndex=subIndex+1;
				name="sub";
				if (dim==1) {class=CAsub1} {class=CAsub2};
			}
			{\add}		{
				index=addIndex;
				addIndex=addIndex+1;
				name="add";
				if (dim==1) {class=CAadd1} {class=CAadd2};
			}
			{\seq}		{
				index=seqIndex;
				seqIndex=seqIndex+1;
				class=CAseq;
				name="seq";
			}
			{\key}		{
				index=keyIndex;
				keyIndex=keyIndex+1;
				class=CAkey;
				name="key";
			}
			{\grain}	{
				index=grainIndex;
				grainIndex=grainIndex+1;
				name="grain";
				if (dim==1) {class=CAgrain1} {class=CAgrain2};
			}
			{\pool}		{
				index=poolIndex;
				poolIndex=poolIndex+1;
				name="pool";
				if (dim==1) {class=CApool1} {class=CApool2};
			};
		if (synthName.isNil.not)	{
			name=synthName.asSymbol;
		} {
			name=(name++index).asSymbol;
		};
		
		mList=this.initList(mList);
		mList.add(class.new(ssName));
		if (dim==1) 
			{mList.at(mIndex).createSynth(clock,length)} 
			{mList.at(mIndex).createSynth(clock,x,y)};
		mDict=this.initDict(mDict);
		mDict.add( name -> mIndex );
		mIndex=mIndex+1;
		^name;	
	}
	

	
}
