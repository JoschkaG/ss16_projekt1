module Division(
	input         clock,
	input         start,
	input  [31:0] a,
	input  [31:0] b,
	output [31:0] q,
	output [31:0] r
);

	reg [31:0] divider;
	reg [31:0] remainder;
	integer takt;	
	
	// TODO Implementierung
	
	always @(start)
	begin
		divider <= 0;
		remainder <= 0;
		takt <= 31;
	end
	
	always @(posedge clock)
	begin
		if (~start)
		begin
		
			if (takt >= 0)
			begin
			
				remainder = remainder << 1;
				remainder = remainder + a[takt];
				
				if (remainder < b)
				begin
					divider[takt] <= 0;
				end
				
				else
				begin
					divider[takt] = 1;
					remainder <= remainder - b;
				end
				
				takt = takt - 1;
				
			end
			
		end
	end
	
	assign q = divider;
	assign r = remainder;

endmodule

