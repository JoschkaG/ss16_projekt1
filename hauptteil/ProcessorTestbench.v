module ProcessorTestbench();

	reg clk;
	reg reset;

	// Instanziere das zu testende Verilog-Modul
	Processor proc(clk, reset);

	initial
		begin
			// Generiere eine Waveform-Ausgabe mit allen (nicht-Speicher) Variablen
			$dumpfile("simres.vcd");
			$dumpvars(0, ProcessorTestbench);

			// Lese auszuführendes Programm ein
			//$readmemh("TestProgramme/Fibonacci.dat", proc.imem.INSTRROM, 0, 5);
			//$readmemh("TestProgramme/Funktionsaufruf.dat", proc.imem.INSTRROM, 0, 4);
			//$readmemh("TestProgramme/Konstanten.dat", proc.imem.INSTRROM, 0, 2);
			$readmemh("TestProgramme/Multiplikation.dat", proc.imem.INSTRROM, 0, 4);
			//$readmemh("TestProgramme/Test_sltu_bne.dat", proc.imem.INSTRROM, 0, 6);

			// Generiere reset-Eingabe
			reset <= 1;
			#5; reset <= 0;
			// Anzahl simulierter Zyklen
			#10000; $finish;
		end

	// Generiere ein periodisches Clock-Signal
	always
		begin
			clk <= 1; #2; clk <= 0; #2;
		end

endmodule

