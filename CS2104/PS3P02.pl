% Name: Ma Zhencai Jayden
% Matric no: U087139J

ma(Prog):-
	arg(1,Prog,Arg1),
	not(atom(Arg1)),
	ma(Arg1),
	arg(2,Prog,Arg2),
	ma(Arg2);
	arg(1,Prog,Arg1),
	atom(Arg1),
	arg(2,Prog,Arg2),
	montage(Arg2,Arg1),!.

% Codes from ps3p01
montage(Prog,Output):-
	process(Prog,Output),!.

process(Prog,Output):-
	functor(Prog, _, 0);

	%Handles rotate atom
	functor(Prog, rotate, 1),
	arg(1,Prog,Arg),
	atom(Arg),
	b_setval(gOutput,Output),
	rotate(Arg);

	%Handles rotate function
	functor(Prog, rotate, 1),
	arg(1,Prog,Arg),
	not(atom(Arg)),
	addLast(Output,1,NewOutput),
	b_setval(gOutput,NewOutput),
	process(Arg,NewOutput),
	b_getval(gOutput,Output2),
	dropLast(Output2,NewOutput2),
	b_setval(gOutput,NewOutput2),
	rotate(Output2);

	%Handles beside atom atom
	functor(Prog, beside, 2),
	arg(1,Prog,Arg1), atom(Arg1),
	arg(2,Prog,Arg2), atom(Arg2),
	b_setval(gOutput,Output),
	beside(Arg1,Arg2);

	%Handles beside function atom
	functor(Prog, beside, 2),
	arg(1,Prog,Arg1), not(atom(Arg1)),
	arg(2,Prog,Arg2), atom(Arg2),
	addLast(Output,1,Output1),
	addLast(Output1,1,NewOutput),
	b_setval(gOutput,NewOutput),
	process(Arg1,NewOutput),
	b_getval(gOutput,Output2),
	dropLast(Output2,NewOutput1),
	dropLast(NewOutput1,NewOutput2),
	b_setval(gOutput,NewOutput2),
	beside(NewOutput,Arg2);

	%Handles beside atom function
	functor(Prog, beside, 2),
	arg(1,Prog,Arg1), atom(Arg1),
	arg(2,Prog,Arg2), not(atom(Arg2)),
	addLast(Output,2,Output1),
	addLast(Output1,1,NewOutput),
	b_setval(gOutput,NewOutput),
	process(Arg2,NewOutput),
	b_getval(gOutput,Output2),
	dropLast(Output2,NewOutput1),
	dropLast(NewOutput1,NewOutput2),
	b_setval(gOutput,NewOutput2),
	beside(Arg1,NewOutput);

	%Handles beside function function
	functor(Prog, beside, 2),
	arg(1,Prog,Arg1), not(atom(Arg1)),
	arg(2,Prog,Arg2), not(atom(Arg2)),
	addLast(Output,1,Output1),
	addLast(Output1,1,NewOutputLeft),
	b_setval(gOutput,NewOutputLeft),
	process(Arg1,NewOutputLeft),
	b_getval(gOutput,OutputR),
	dropLast(OutputR,OutputR1),
	dropLast(OutputR1,OutputR2),
	addLast(OutputR2,2,OutputR3),
	addLast(OutputR3,1,NewOutputRight),
	b_setval(gOutput,NewOutputRight),
	process(Arg2,NewOutputRight),
	b_getval(gOutput,OutputF),
	dropLast(OutputF,OutputF1),
	dropLast(OutputF1,OutputFinal),
	b_setval(gOutput,OutputFinal),
	beside(NewOutputLeft,NewOutputRight)
	.

rotate(Image):-
	atom(Image),
	write('convert -rotate 90 '),
	write(Image),
	write('.jpg '),
	b_getval(gOutput,Output),
	write(Output),
	write('.jpg'),
	writeln('').

beside(Image1,Image2):-
	atom(Image1),
	atom(Image2),
	b_getval(gOutput,Output),
	addLast(Output,1,NewOutput),
	b_setval(gOutput,NewOutput),

	scale(Image1),
	b_getval(gOutput,Output1),
	dropLast(Output1,Output2),
	addLast(Output2,2,NewOutput1),
	b_setval(gOutput,NewOutput1),

	scale(Image2),
	b_getval(gOutput,Output3),
	dropLast(Output3,NewOutput2),
	b_setval(gOutput,NewOutput2),

	appendImage(Output1,NewOutput1).

scale(Image):-
	write('convert -scale 50%%x50%% '),
	write(Image),
	write('.jpg '),
	b_getval(gOutput,Output),
	write(Output),
	write('.jpg'),
	writeln('').

appendImage(Image1,Image2):-
	write('convert +append '),
	write(Image1),
	write('.jpg '),
	write(Image2),
	write('.jpg '),
	b_getval(gOutput,Output),
	write(Output),
	write('.jpg'),
	writeln('').

dropLast(Image,Result):-
	name(Image,ImageChar),
	append(ResultChar,[49],ImageChar),
	name(Result,ResultChar);
	name(Image,ImageChar),
	append(ResultChar,[50],ImageChar),
	name(Result,ResultChar).

addLast(Image,Add,Result):-
	name(Image,ImageChar),
	name(Add,AddChar),
	append(ImageChar,AddChar,ResultChar),
	name(Result,ResultChar).
