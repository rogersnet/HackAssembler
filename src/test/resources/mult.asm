// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

	//assign value of RAM[2] to 0
	@0
	D = A // D = 0

	@R2
	M = D // RAM[2] = 0

	//if value in RAM[1] is 0, end the program
	@R1
	D = M  // D = RAM[1]

	@END
	D;JEQ

	//assign the loop counter variable to value in RAM[1]
	@R1
	D = M  // D = count

	@count
	M = D // count = RAM[1]

(LOOP)
	//RAM[2] = RAM[2] + RAM[0]
	@R0
	D = M

	@R2
	M = D + M

	//assign count = count - 1
	@count
	D = M       // D = count
	M = D - 1 	// count = count - 1

	@LOOP
	D - 1;JGT

(END)
	@END
	0;JMP