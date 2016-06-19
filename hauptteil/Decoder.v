module Decoder(
	input     [31:0] instr,      // Instruktionswort
	input            zero,       // Liefert aktuelle Operation im Datenpfad 0 als Ergebnis?
	output reg       memtoreg,   // Verwende ein geladenes Wort anstatt des ALU-Ergebis als Resultat
	output reg       memwrite,   // Schreibe in den Datenspeicher
	output reg       dobranch,   // Führe einen relativen Sprung aus
	output reg       alusrcbimm, // Verwende den immediate-Wert als zweiten Operanden
	output reg [4:0] destreg,    // Nummer des (möglicherweise) zu schreibenden Zielregisters
	output reg       regwrite,   // Schreibe ein Zielregister
	output reg       dojump,     // Führe einen absoluten Sprung aus
	output reg 		 dojumpreg,  // Jump zu Register
	output reg [2:0] alucontrol,  // ALU-Kontroll-Bits
	output reg [1:0] multcont,	 //Control Bits für MultHi und MultLo
	output reg 		 lui,		 //Load Control
	output reg 		 ori 		 //Ori Control
);
	// Extrahiere primären und sekundären Operationcode
	wire [5:0] op = instr[31:26];
	wire [5:0] funct = instr[5:0];

	always @*
	begin
		case (op)
			6'b000000: // Rtype Instruktion
				begin
					regwrite = 1;
					destreg = instr[15:11];
					alusrcbimm = 0;
					dobranch = 0;
					memwrite = 0;
					memtoreg = 0;
					dojump = 0;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					case (funct)
						6'b100001: alucontrol = 'b010; //Addition
						6'b100011: alucontrol = 'b110; //Subtraktion
						6'b011001: alucontrol = 'b011; //Multiplikation
						6'b010000:					   //MultHi
							begin
								alucontrol = 'bxxx;
								multcont = 'b01;
							end
						6'b010010:					   //MultLo
							begin
								alucontrol = 'bxxx;
								multcont = 'b10;
							end
						6'b100100: alucontrol = 'b000; //Logisches Und
						6'b100101: alucontrol = 'b001; //Logisches Oder
						6'b101011: alucontrol = 'b111; //Less than
						6'b001000: 
							begin
							alucontrol = 'bxxx;
							dojumpreg = 1;
							end
						default:   alucontrol = 'bxxx; //Undefined
					endcase
				end
			6'b100011, // Lade Datenwort aus Speicher
			6'b101011: // Speichere Datenwort
				begin
					regwrite = ~op[3];
					destreg = instr[20:16];
					alusrcbimm = 1;
					dobranch = 0;
					memwrite = op[3];
					memtoreg = 1;
					dojump = 0;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'b010;    // TODO // Addition effektive Adresse: Basisregister + Offset
				end
			6'b000100: // Branch Equal
				begin
					regwrite = 0;
					destreg = 5'bx;
					alusrcbimm = 0;
					dobranch = zero; // Gleichheitstest
					memwrite = 0;
					memtoreg = 0;
					dojump = 0;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'b110; // TODO // Subtraktion
				end
			6'b000101: // Branch Not Equal
				begin
					regwrite = 0;
					destreg = 5'bx;
					alusrcbimm = 0;
					dobranch = ~zero; // Gleichheitstest
					memwrite = 0;
					memtoreg = 0;
					dojump = 0;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'b110; // TODO // Subtraktion
				end
			6'b001001: // Addition immediate unsigned
				begin
					regwrite = 1;
					destreg = instr[20:16];
					alusrcbimm = 1;
					dobranch = 0;
					memwrite = 0;
					memtoreg = 0;
					dojump = 0;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'b010; // TODO // Addition
				end
			6'b000010: // Jump immediate
				begin
					regwrite = 0;
					destreg = 5'bx;
					alusrcbimm = 0;
					dobranch = 0;
					memwrite = 0;
					memtoreg = 0;
					dojump = 1;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'bxxx; // TODO
				end
			6'b000011: // Jal
				begin
					regwrite = 1;
					destreg = 31;
					alusrcbimm = 0;
					dobranch = 0;
					memwrite = 0;
					memtoreg = 0;
					dojump = 1;
					dojumpreg = 0;
					lui = 0;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'bxxx; // TODO
				end
			default: // Default Fall
				begin
					regwrite = 1'bx;
					destreg = 5'bx;
					alusrcbimm = 1'bx;
					dobranch = 1'bx;
					memwrite = 1'bx;
					memtoreg = 1'bx;
					dojump = 1'bx;
					dojumpreg = 1'bx;
					lui = 1'bx;
					ori = 1'bx;
					multcont = 2'bx;
					alucontrol = 'bxxx;// TODO
				end
			6'b001111: // Lui
				begin
					regwrite = 1;
					destreg = instr[20:16];
					alusrcbimm = 0;
					dobranch = 0;
					memwrite = 0;
					memtoreg = 0;
					dojump = 0;
					dojumpreg = 0;
					lui = 1;
					ori = 0;
					multcont = 'b00;
					alucontrol = 'bxxx;
				end
			6'b001101: // ori
				begin
					regwrite = 1;
					destreg = instr[20:16];
					alusrcbimm = 1;
					dobranch = 0;
					memwrite = 0;
					memtoreg = 0;
					dojump = 0;
					dojumpreg = 0;
					lui = 0;
					ori = 1;
					multcont = 'b00;
					alucontrol = 'b001;
				end
		endcase
	end
endmodule

