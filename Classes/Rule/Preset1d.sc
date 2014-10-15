// Andrés Pérez, 2011
// www.andresperezlopez.com

// Preset1d.sc
// Structure for rule presets

// all presets from Mirek Wojtowicz, "Mirek's Cellebration"
// http://www.mirekw.com/ca/rullex_1dbi.html

Preset1d {	
//	classvar k;
//	classvar
	classvar <type=\standard;

	*list {
		^this.subclasses;
	}
}

BermudaTriangle : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0];
}
BrownianMotion : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0, 0, 1, 0, 0, 1, 0, 0 ];
}
ChaoticGliders : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,0,1,1,0,1,1,0,0,1,1,1,0,0,0,1,1,1,0,0,1,0,0,0,1,1,0,0,1,0];
}
FiliformGliders1 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,1,0,0,0,1,1,1,1,0,0,1,1,0,0,0];
}
FiliformGliders2 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,0,1,1,1,0,0,0,1,1,0,1,0,1,0,0,1,0,0,1,1,0,1,1,0,0,1,1,0,0,0];
}
CompoundGlider : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,0,0,1,0,0,1,1,1,1,0,1,1,0,1,0,1,1,1,0,0,0,1,0,0,0,0,0,1,1,0];
}
FishBones : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,0,1,1,1,1,1,0,0,0,0,1,1,0,0,1,0,0,1,1,0,1,0,1,1,0,1,1,0,0,0];
}
FishingNet : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0,1,1,0,1,1,1,0];
}
GliderP106 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,1,0,1,0,1,0,0,0,1,1,1,1,0,1,0,0,1,1,1,0,0,1,1,1,0,1,0,0,0];
}
GliderGunP168 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,1,0,1,1,0,0,0,0,0,1,1,1,1,0,0,1,0,1,0,0,1,1,1,0,1,0,1,0,0,0];
}
HeavyTriangles : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,0,1,1,0];
}
InverseGliders : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,1,1,0,1,1,0,0,0,0,0,1,0,1,0,1,0,0,1,0,1,1,0,1,1,1,1,1,0,0,1];
}
Kites : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,1,1,1,1,1,1,0,0,0,1,0,1,0,0,1,0,1,1,1,0,0,1,1,0,1,1,0,0,0];
}
LinearA : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0,1,0,1,1,0,1,0];
}
LinearB : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[1,0,0,1,0,1,1,0];
}
PascalTriangle : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,0,0,1,0];
}
Plaitwork : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,1,0,1,1,1,0,1,0,1,0,1,0,0,0,1,1,0,0,1,1,0,1,0,0,0,1,0,1,0,0];
}
Raindrops : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,0,0,0,1,1,0,0,1,1,0,1,0,0,0,1,1,1,0,1,1,0,1,0,0,0,1,0,1,0,0];
}
Randomizer1 : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,1,1,1,0];
}
Randomizer2 : Preset1d {
	classvar <k=2;
	classvar <r=1;
//	classvar <type=\W;
	classvar <rule=#[0,0,1,0,1,1,0,1];
}
ReliefGliders : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,1,0,1,0,0,1,0,1,0,0,0,1,1,1,1,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0];
}
Scaffolding : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,0,1,0,0,0,1,0,1,0,0];
}
SolitonsA : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,1,1,1,1,1,1,0,0,0,1,0,1,0,0,0,0,1,1,0,0,0,1,1,0,0,1,0,0,0];
}
SolitonsA1 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,1,1,1,1,1,1,0,0,0,1,0,1,0,0,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0];
}
SolitonsB : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,1,1,1,1,0,0,1,1,0,0,0,1,1,0,0,1,1,0,1,0,1,1,1,0,0,0,0,1,0,0];
}
SolitonsB1 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,1,1,1,1,1,0,1,1,1,0,1,1,1,0,0,1,1,0,1,0,1,1,1,0,0,0,0,1,0,0];
}
SolitonsB3 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,1,1,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,0,1,0,1,1,0,0,1,0,0,0];
}
SolitonsC1 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,0,1,1,1,1,1,0,0,1,0,1,0,1,0,1,0,0,1,1,1,0,0,1,1,0,0,1,0,0,0];
}
SolitonsC2 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,1,1,0,1,0,0,1,0,0,1,1,0,0,1,0,1,1,1,1,0,1,1,0,0,1,0,0,0];
}
SolitonsD2 : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,0,0,1,1,1,0,1,0,0,0,0,0,1,1,0,0,1,0,1,1,0,1,0,1,1,0,1,1,0,0,0];
}
SolitonsE : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,1,1,1,1,0,1,1,0,1,0,0,0,1,0,0,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0];
}
SolitonsF : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,0,1,1,1,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,0,1,0,1,1,0,0,1,0,0,0];
}
StableGliders : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[0,1,1,1,1,1,1,0,1,0,0,0,0,1,1,0,1,0,0,1,0,1,1,0,1,1,0,1,1,1,1,0];
}
Threads : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,0,1,0,1,1,1,1,0,0,0,1,1,1,0,1,1,0,0,1,1,1,0,1,1,1,0,0,1,0,0];
}
TriangularGliders : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,1,1,0,0,0,0,0,1,0,0,0,1,0,0,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1];
}
ZigZags : Preset1d {
	classvar <k=2;
	classvar <r=2;
//	classvar <type=\W;
	classvar <rule=#[1,0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,0,0,0,1,1,0,1,0,0,1,0,0,1,0,0,0];
}
