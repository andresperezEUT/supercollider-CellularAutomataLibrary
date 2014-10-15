// Andrés Pérez, 2011
// www.andresperezlopez.com

// Preset2d.sc
// Structure for rule presets

// taken from Cellular Automata Rules Lexicon, "Mirek's Cellebration"
// http://www.mirekw.com/ca/ca_rules.html

Preset2d {	
	classvar <type;
	classvar <character;
	classvar <k;
	classvar <r;
	classvar <neighborhoodType;
	classvar <rule;

	*list {
		^this.subclasses;
	}
}

Preset2dF : Preset2d {

}

// by Brian Silverman
Balloons : Preset2dF {
	classvar <type=\firing;
	classvar <character=\exploding;
	classvar <k=16;
	classvar <r=1;
	classvar <rule=#[[0,0,15,0,0,0,5,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[4,4,8,4,4,4,4,4,4],[5,5,5,5,5,7,7,9,11],[2,2,2,2,2,2,2,2,2],[5,5,5,5,5,13,13,9,11],[8,8,10,8,8,8,8,8,8],[2,2,2,2,2,9,13,9,1],[10,10,0,10,10,10,10,10,10],[4,14,14,14,14,14,14,14,11],[2,12,4,12,12,12,12,12,12],[6,6,6,6,13,13,13,9,11],[14,14,14,12,14,14,14,14,14],[2,2,2,2,2,2,2,2,2]];
}

// by George Maydwell
BusyBrain : Preset2dF {
	classvar <type=\firing;
	classvar <character=\chaotic;
	classvar <k=3;
	classvar <r=1;
	classvar <rule=#[[0,0,1,2,0,2,2,2,2],[2,2,2,1,0,2,2,2,2],[0,0,0,0,1,2,2,1,2]];
}

// by Alastair Couper
Cheops : Preset2dF {
	classvar <type=\firing;
	classvar <character=\expanding;
	classvar <k=10;
	classvar <r=1;
	classvar <rule=#[[0,4,1,9,8,0,0,0,0],[5,0,9,7,0,6,0,9,8],[8,0,0,0,0,0,0,0,0],[0,0,2,0,0,6,0,0,4],[3,0,0,0,3,0,1,0,0],[4,0,3,0,9,0,6,1,0],[0,5,0,0,0,0,4,1,0],[2,7,0,2,6,3,8,4,6],[1,0,0,0,0,0,0,0,0],[0,0,0,6,7,0,8,5,3]];
}

// by Rudy Rucker
EcoLiBra : Preset2dF {
	classvar <type=\firing;
	classvar <character=\chaotic;
	classvar <k=16;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,0,7,0,0,0,15,15,0],[0,0,0,0,0,0,0,0,0],[15,15,15,15,15,2,2,15,15],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[12,12,12,12,12,12,12,12,12],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[15,0,15,15,15,2,15,15,15]];
}

// by Rudy Rucker
FireSticks : Preset2dF {
	classvar <type=\firing;
	classvar <character=\gliding;
	classvar <k=16;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,0,15,15,0,0,0,0,0],[0,0,15,15,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,5,0,0,0,0,0],[6,6,0,6,6,6,6,6,6],[0,0,0,7,0,0,0,0,0],[8,8,6,8,8,8,8,8,8],[0,0,0,9,0,0,0,0,0],[2,2,8,2,2,2,2,2,2],[0,0,0,11,0,0,0,0,0],[4,4,10,4,4,4,4,4,4],[0,0,0,13,0,0,0,0,0],[14,14,12,14,14,14,14,14,14],[0,0,0,15,0,0,0,0,0],[10,10,14,10,10,10,10,10,10],[12,12,0,15,12,12,12,12,12]];
}

// by Rudy Rucker
Ladders : Preset2dF {
	classvar <type=\firing;
	classvar <character=\chaotic;
	classvar <k=16;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,6,5,0,0,2,15,5,0],[0,0,0,0,0,0,0,0,0],[0,0,0,8,7,15,0,15,0],[0,0,6,0,0,0,0,0,3],[0,0,0,0,0,0,0,0,0],[8,0,0,0,0,0,0,0,0],[8,4,2,5,6,0,0,0,0],[4,0,11,0,0,0,0,0,0],[0,0,0,0,0,0,15,4,0],[0,8,0,15,5,0,0,0,0],[4,10,0,0,4,5,0,0,4],[0,8,8,0,0,12,4,6,0],[0,0,0,10,2,10,6,6,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,9,0,11,3,0],[9,0,0,0,14,0,0,6,0]];
}

// by Rudy Rucker
Piranha : Preset2dF {
	classvar <type=\firing;
	classvar <character=\chaotic;
	classvar <k=16;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,6,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0],[0,11,0,0,0,0,0,0,0],[7,0,6,0,0,0,0,0,0],[0,0,0,3,0,0,0,0,9],[0,0,6,0,0,0,0,0,0],[0,0,0,0,3,0,0,0,0],[5,0,0,0,0,0,0,0,0],[8,0,4,0,0,0,0,0,0],[0,0,0,0,0,0,0,3,0],[0,0,0,0,0,0,0,0,0,1]];
}

// by Mirek Wojtowicz
Strangers : Preset2dF {
	classvar <type=\firing;
	classvar <character=\gliding;
	classvar <k=12;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,4,1,9,8,0,0,0,0],[5,0,9,7,0,6,0,9,8],[2,5,0,4,0,0,0,0,0],[0,10,2,10,0,6,0,0,4],[3,0,10,0,3,0,1,10,0],[4,0,3,10,9,0,6,1,0],[0,5,0,0,0,0,4,1,0],[2,7,0,2,6,3,8,4,6],[1,0,0,0,0,0,0,0,0],[0,0,0,6,7,0,8,5,3],[9,0,0,5,0,4,0,0,5],0,0,0,0,0,0,9,0,0];
}

// by Brian Silverman
// 0: background, 1: electron head, 2: electron tail, 3: wire
WireWorld : Preset2dF {
	classvar <type=\firing;
	classvar <character=\stable;
	classvar <k=4;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,0,0,0,0,0,0,0,0],[2,2,2,2,2,2,2,2,2],[3,3,3,3,3,3,3,3,3],[3,1,1,3,3,3,3,3,3]];
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Preset2dL : Preset2d {
//	classvar <type;
//	classvar <character;
//	classvar <r;
//	classvar <neighborhoodType;
//	classvar <rule;
}

Life34 : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4],[3,4]];
}
Amoeba : Preset2dL {
	classvar <type=\life;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,3,5,8],[3,5,7]];
}
Assimilation : Preset2dL {
	classvar <type=\life;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[4,5,6,7],[3,4,5]];
}
Coagulations : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,5,6,7,8],[3,7,8]];
}
// by John Conway
ConwaysLife : Preset2dL {
	classvar <type=\life;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3],[3]];
}
Coral : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[4,5,6,7,8],[3]];
}
// by Nathan Thompson
DayAndNight : Preset2dL {
	classvar <type=\life;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,6,7,8],[3,6,7,8]];
}
// by Dean Hickerson
Diamoeba : Preset2dL {
	classvar <type=\life;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[5,6,7,8],[3,5,6,7,8]];
}
// by Janko Gravner
Flakes : Preset2dL {
	classvar <type=\life;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,1,2,3,4,5,6,7,8],[3]];
}
// by Kellie Evans
Gnarl : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1],[1]];
}
// by Nathan Thompson
HighLife : Preset2dL {
	classvar <type=\life;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3],[3,6]];
}
// by Andrew Trevorrow
LongLife : Preset2dL {
	classvar <type=\life;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[5],[3,4,5]];
}
Maze : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,2,3,4,5],[3]];
}
// by Charles A. Rockafellor
Mazectric : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,2,3,4],[3]];
}
Move : Preset2dL {
	classvar <type=\life;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,4,5],[3,6,8]];
}
PseudoLife : Preset2dL {
	classvar <type=\life;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,8],[3,5,7]];
}
Replicator : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,3,5,7],[1,3,5,7]];
}
// by Brian Silverman
Seeds2 : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[],[2]];
}
Serviettes : Preset2dL {
	classvar <type=\life;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[],[2,3,4]];
}
Stains : Preset2dL {
	classvar <type=\life;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,5,6,7,8],[3,6,7,8]];
}
// by David Macfarlane
WalledCities : Preset2dL {
	classvar <type=\life;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,4,5],[4,5,6,7,8]];
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Preset2dG : Preset2d {
//	classvar <type;
//	classvar <character;
//	classvar <r;
//	classvar <neighborhoodType;
//	classvar <rule;
}

// by Mirek Wojtowicz
Banners : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,6,7],[3,4,5,7],5];
}
// a Zhabotinsky reaction
BelZhab : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3],[2,3],8];
}
// a Zhabotinsky reaction by Jason Rampe
BelZhabSediment : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,4,5,6,7,8],[2,3],8];
}
// by John Elliott
Bloomerang : Preset2dG {
	classvar <type=\generations;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,4],[3,4,6,7,8],24];
}
// by Mirek Wojtowicz
Bombers : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5],[2,4],25];
}
// by Michael Sweney
Brain6 : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[6],[2,4,6],3];
}
// by Brian Silverman
BriansBrain : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[],[2],3];
}
// by Michael Sweney
Burst : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,2,3,5,6,7,8],[3,4,6,8],9];
}
// by Michael Sweney
Burst2 : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,5,6,7,8],[3,4,6,8],9];
}
// by Mirek Wojtowicz
Caterpillars : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,2,4,5,6,7],[3,7,8],4];
}
Chenille : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,5,6,7,8],[2,4,5,6,7],6];
}
// by Charles A. Rockafellor
CircuitGenesis : Preset2dG {
	classvar <type=\generations;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3,4,5],[1,2,3,4],8];
}
// by Rudy Rucker
Cooties : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3],[2],8];
}
// by Michael Sweney
EbbAndFlow : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,1,2,4,7,8],[3,6],18];
}
// by Michael Sweney
EbbAndFlow2 : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,1,2,4,6,8],[3,7],18];
}
// by Rudy Rucker and John Walker
Faders : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2],[2],25];
}
// by John Elliott
Fireworks : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2],[1,3],21];
}
// by Charles A. Rockafellor
FlamingStarbows : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,7],[2,3],8];
}
// by Scott Robert Ladd
Frogs : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,2],[3,4],3];
}
// a Zhabotinsky reaction by Mirek Wojtowicz
FrozenSpirals : Preset2dG {
	classvar <type=\generations;
	classvar <character=\cyclic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,5,6],[2,3],6];
}
// by John Elliott
Glisserati : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,3,5,6,7,8],[2,4,5,6,7],7];
}
// by John Elliott
Glissergy : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,3,5,6,7,8],[2,4,5,6,7],5];
}
// by Mirek Wojtowicz
Lava : Preset2dG {
	classvar <type=\generations;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,2,3,4,5],[4,5,6,7,8],8];
}
// by Anders Starmark
Lines : Preset2dG {
	classvar <type=\generations;
	classvar <character=\stable;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,1,2,3,4,5],[4,5,8],3];
}
// by Mirek Wojtowicz
LivingOnTheEdge : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5],[3],6];
}
// by Charles A. Rockafellor
MeteorGuns : Preset2dG {
	classvar <type=\generations;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,1,2,4,5,6,7,8],[3],8];
}
// by Mirek Wojtowicz
Nova : Preset2dG {
	classvar <type=\generations;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[4,5,6,7,8],[2,4,7,8],25];
}
// by John Elliott
OrthoGo : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3],[2],4];
}
// by Mirek Wojtowicz
PrairieOnFire : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5],[3,4],6];
}
// a Zhabotinsky reaction by Rudy Rucker and John Walker
RainZha : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2],[2,3],8];
}
// by Brian Prentice
Rake : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,6,7],[2,6,7,8],6];
}
// by John Elliott
SediMental : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[4,5,6,7,8],[2,5,6,7,8],4];
}
// by Brian Prentice
Snake : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,3,4,6,7],[2,5],6];
}
// by John Elliott
SoftFreeze : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,3,4,5,8],[3,8],6];
}
Spirals : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2],[2,3,4],5];
}
// by Mirek Wojtowicz
StarWars : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5],[2],4];
}
// by Rudy Rucker
Sticks : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5,6],[2],6];
}
// by Scott Robert Ladd
Swirl : Preset2dG {
	classvar <type=\generations;
	classvar <character=\chaotic;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[2,3],[3,4],8];
}
// by John Elliott
ThrillGrill : Preset2dG {
	classvar <type=\generations;
	classvar <character=\expanding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,2,3,4],[3,4],48];
}
// by John Elliott
Transers : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5],[2,6],5];
}
// by Michael Sweney
Transers2 : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[0,3,4,5],[2,6],5];
}
// by Mirek Wojtowicz
Wanderers : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,5],[3,4,6,7,8],5];
}
// by Mirek Wojtowicz
Worms : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[3,4,6,7],[2,5],6];
}
// by John Elliott
Xtasy : Preset2dG {
	classvar <type=\generations;
	classvar <character=\exploding;
	classvar <r=1;
	classvar <neighborhoodType=\moore;
	classvar <rule=#[[1,4,5,6],[2,3,5,6],16];
}

/////////////////////////////////////////////////////////////////////////////////////////

Preset2dM : Preset2d {

	classvar <character=\margolus;
}
// by Edward Fredkin
BBM : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,8,4,3,2,5,9,7,1,6,10,11,12,13,14,15];
}
// by Tim Tyler
BounceGas : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,8,4,3,2,5,9,14,1,6,10,13,12,11,7,15];
}
// by Tim Tyler
BounceGas2 : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,8,4,12,2,10,9,7,1,6,5,11,3,13,14,15];
}
// by Margolus and Toffoli
Critters : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[15,14,13,3,11,5,6,1,7,9,10,2,12,4,8,0];
}
// by Hardy, Pazzis and Pomeau
HPPGas : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,8,4,12,2,10,9,14,1,6,5,13,3,11,7,15];
}
// by Andres Perez
Inverted : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0];
}
// by Tim Tyler
Rotations : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,2,8,12,1,10,9,11,4,6,5,14,3,7,13,15];
}
// by Tim Tyler
Rotations2 : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,2,8,12,1,10,9,13,4,6,5,7,3,14,11,15];
}
// by Edward Fredkin
Rotations3 : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,4,1,10,8,3,9,11,2,6,12,14,5,7,13,15];
}
// by Tim Tyler
Rotations4 : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,4,1,12,8,10,6,14,2,9,5,13,3,11,7,15];
}
// by Mirek Wojtowicz
Sand : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,4,8,12,4,12,12,13,8,12,12,14,12,13,14,15];
}
// by Tim Tyler
StringThing : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,1,2,12,4,10,9,7,8,6,5,11,3,13,14,15];
}
// by Tim Tyler
StringThing2 : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,1,2,12,4,10,6,7,8,9,5,11,3,13,14,15];
}
// by Margolus and Toffoli
SwapOnDiag : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15];
}
// by Margolus and Toffoli
Tron : Preset2dM {
	classvar <type=\margolus;
	classvar <rule=#[15,1,2,3,4,5,6,7,8,9,10,11,12,13,14,0];
}
