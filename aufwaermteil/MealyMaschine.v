module MealyPattern(
	input        clock,
	input        i,
	output  [1:0] o
);

reg p,q;


initial begin
	p = 0;
	q = 0; 
end


		always @(posedge clock) begin
			p <= i;
			q <= p;
		end
		
	assign o[1] = i & ~p & q;
	assign o[0] = ~i & p & ~q;
	
endmodule

module MealyPatternTestbench();

	reg clk,in;

	initial begin
		$dumpfile("Mealy.vcd");
		$dumpvars;
		in <= 1'b0;
		#4
		in <= 1'b1;
		#4
		in <= 1'b1;
		#4
		in <= 1'b0;
		#4
		in <= 1'b1;
		#4
		in <= 1'b0;
		#4
		in <= 1'b1;
		#4
		in <= 1'b0;
		#4
		in <= 1'b1;
		#4
		in <= 1'b1;
		#4
		$finish;
	end

	always
	begin
		clk <= 1'b0; #2; clk <= 1'b1; #2;
	end

	wire [1:0] o;
	wire i;

	MealyPattern machine(.clock(clk), .i(in), .o(o));

	reg [0:8] e;
	reg [0:8] s [1:0];
	reg [0:8] r;

	initial begin
		r = {9'b101001101};
	end

endmodule
