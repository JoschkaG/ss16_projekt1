module MealyPattern(
	input        clock,
	input        i,
	output  [1:0] o
);

reg p,q,r,s;


initial begin
	p = 'b1;
	q = 'b1;
	r = 'b0;
	s = 'b0;
end


		always @(posedge clock) begin
			p <= i; r <= i;
			q <= p; s <= r;
		end
		
	assign o[1] = i & ~r & s;
	assign o[0] = ~i & p & ~q;
	
endmodule

module MealyPatternTestbench();

	reg clk,in;

	initial begin
		$dumpfile("Mealy.vcd");
		$dumpvars;
		//0110101011
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


endmodule

