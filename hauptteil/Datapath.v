module Datapath(
	input         clk, reset,
	input         memtoreg,
	input         dobranch,
	input         alusrcbimm,
	input  [4:0]  destreg,
	input         regwrite,
	input         jump,
	input 		  dojumpreg,
	input  [2:0]  alucontrol,
	input		  lui,
	input		  ori,
	output        zero,
	output [31:0] pc,
	input  [31:0] instr,
	output [31:0] aluout,
	output [31:0] writedata,
	input  [31:0] readdata
);
	wire [31:0] pc;
	wire [31:0] signimm;
	wire [31:0] srca, srcb, srcbimm;
	wire [31:0] result;
	wire  [31:0] oldpc;

	// Fetch: Reiche PC an Instruktionsspeicher weiter und update PC
	ProgramCounter pcenv(clk, reset, dobranch, signimm, jump, dojumpreg, srca, instr[25:0],oldpc, pc);

	// Execute:
	// (a) Wähle Operanden aus
	SignExtension se(instr[15:0],ori, signimm);
	assign srcbimm = alusrcbimm ? signimm : srcb;
	// (b) Führe Berechnung in der ALU durch
	ArithmeticLogicUnit alu(srca, srcbimm, alucontrol, aluout, zero);
	// (c) Wähle richtiges Ergebnis aus
	assign result = memtoreg ? readdata : (lui ? (instr[15:0]<<16) : jump ? oldpc : aluout);

	// Memory: Datenwort das zur (möglichen) Speicherung an den Datenspeicher übertragen wird
	assign writedata = srcb;

	// Write-Back: Stelle Operanden bereit und schreibe das jeweilige Resultat zurück
	RegisterFile gpr(clk, regwrite, instr[25:21], instr[20:16],
	               destreg, result, srca, srcb);
endmodule

module ProgramCounter(
	input         clk,
	input         reset,
	input         dobranch,
	input  [31:0] branchoffset,
	input         dojump,
	input		  dojumpreg,
	input  [31:0] srca,
	input  [25:0] jumptarget,
	output [31:0] oldpc,
	output [31:0] progcounter
);
	reg  [31:0] pc;
	wire [31:0] incpc, branchpc, nextpc;

	// Inkrementiere Befehlszähler um 4 (word-aligned)
	Adder pcinc(.a(pc), .b(32'b100), .cin(1'b0), .y(incpc));
	// Berechne mögliches (PC-relatives) Sprungziel
	Adder pcbranch(.a(incpc), .b({branchoffset[29:0], 2'b00}), .cin(1'b0), .y(branchpc));
	// Wähle den nächsten Wert des Befehlszählers aus
	assign nextpc = dojumpreg ? srca :
					dojump   ? {incpc[31:28], jumptarget, 2'b00} :
	                dobranch ? branchpc :
	                           incpc;
	assign oldpc = dojump ? incpc : 0 ;
	// Der Befehlszähler ist ein Speicherbaustein
	always @(posedge clk)
	begin
		if (reset) begin // Initialisierung mit Adresse 0
			pc <= 'b0;
		end else begin
			pc <= nextpc;
		end
	end

	// Ausgabe
	assign progcounter = pc;

endmodule

module RegisterFile(
	input         clk,
	input         we3,
	input  [4:0]  ra1, ra2, wa3,
	input  [31:0] wd3,
	output [31:0] rd1, rd2
);
	reg [31:0] registers[31:0];

	always @(posedge clk)
		if (we3) begin
			registers[wa3] <= wd3;
		end

	assign rd1 = (ra1 != 0) ? registers[ra1] : 0;
	assign rd2 = (ra2 != 0) ? registers[ra2] : 0;
endmodule

module Adder(
	input  [31:0] a, b,
	input         cin,
	output [31:0] y,
	output        cout
);
	assign {cout, y} = a + b + cin;
endmodule

module SignExtension(
	input  [15:0] a,
	input 		  o,
	output [31:0] y
);
	assign y = o ? {16'b0, a} : {{16{a[15]}}, a};
endmodule

module ArithmeticLogicUnit(
	input  [31:0] a, b,
	input  [2:0]  alucontrol,
	output [31:0] result,
	output        zero
);

reg [31:0] r,hi,lo;
reg z;

always @*
begin
	case (alucontrol)
			3'b000: 
				begin
				r = a & b;
				end
			3'b001: 
				begin
				r = a | b;
				end
			3'b010:
				begin
				r = a + b;
				end
			3'b110:
				begin
				r = a - b;
				end
			3'b011:
				begin
				{hi,lo} = a*b;		
				end	
			3'b111:
				begin
				r = a < b;
				end
			3'b100:
				begin
				r = lo;
				end
			3'b101:
				begin
				r = hi;
				end
	endcase
	if(r==0) 
	begin
	z = 1;	
	end
	else begin
	z = 0;
	end
end

assign result = r ;
assign zero = z;

endmodule
