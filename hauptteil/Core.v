module MIPScore(
	input clk,
	input reset,
	// Kommunikation Instruktionsspeicher
	output [31:0] pc,
	input  [31:0] instr,
	// Kommunikation Datenspeicher
	output        memwrite,
	output [31:0] aluout, writedata,
	input  [31:0] readdata
);
	wire       memtoreg, alusrcbimm, regwrite, dojump, dojumpreg, dobranch, zero,ori,lui;
	wire [1:0] multcont;
	wire [4:0] destreg;
	wire [2:0] alucontrol;

	Decoder decoder(instr, zero, memtoreg, memwrite,
	                dobranch, alusrcbimm, destreg,
	                regwrite, dojump, dojumpreg, alucontrol,multcont,lui,ori);
	Datapath dp(clk, reset, memtoreg, dobranch,
	            alusrcbimm, destreg, regwrite, dojump, dojumpreg,
	            alucontrol,multcont,lui,ori,
	            zero, pc, instr,
	            aluout, writedata, readdata);
endmodule

