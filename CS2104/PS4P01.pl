% PS4P01
% Name: Ma Zhencai Jayden
% Matric no: U087139J

fractal(F=FEqn;FEqns,Level,Unit):-
	writeHeader(Level),
	fractal(F=(FEqn;FEqns),Level,Unit),!.

fractal(F=FEqn,Level,Unit):-
	writeHeader(Level),
	fractal(FEqn,F=FEqn,Level,Unit),!.

fractal(_,Eqn,1,Unit):-
	writeFractal(Eqn,Unit),
	write('time.sleep(3)'),
	told,!.

fractal(FullFEqn,F=FEqn,Level,Unit):-
	fractal_r(FullFEqn,F,FEqn,Result),
	NewLevel is Level - 1,
	fractal(FullFEqn,F=Result,NewLevel,Unit).

fractal_r(FullFEqn,F,F;FEqn,FullFEqn;FEqnResult):-
	fractal_r(FullFEqn,F,FEqn,FEqnResult).

fractal_r(FullFEqn,F,NotF;FEqn,NotFResult;FEqnResult):-
	fractal_r(FullFEqn,F,NotF,NotFResult),
	fractal_r(FullFEqn,F,FEqn,FEqnResult).

fractal_r(FullFEqn,F,F,FullFEqn).
fractal_r(_,_,NotF,NotF).

%sets the size of screen to cater for turtle of diff sizes
%(only catering for a fractal with 4 g)
%This however only works on fractals that grow outwards.
writeHeader(Level):-
	((Level > 1 ->
	SizeOfScreen is 100 * Level * Level * Level * Level);
	(Level =:= 1 ->
	SizeOfScreen is 600)),
	ScreenPos is SizeOfScreen / -2,
	tell('out.py'),
	writeln('from turtle import *'),
	writeln('import time'),
	write('Screen().setworldcoordinates('),
	write(ScreenPos),
	write(','),
	write(ScreenPos),
	write(','),
	write(SizeOfScreen),
	write(','),
	write(SizeOfScreen),
	writeln('),'),
	writeln('delay(0)').


writeFractal(Fractal=FractalEqn,Unit):-
	writeFractal(Fractal,FractalEqn,Unit).

writeFractal(Fractal,Fractal;FractalEqn,Unit):-
	writeForward(Unit),
	writeFractal(Fractal,FractalEqn,Unit).

writeFractal(Fractal,NotFractal;FractalEqn,Unit):-
	writeFractal(Fractal,NotFractal,Unit),
	writeFractal(Fractal,FractalEqn,Unit).

writeFractal(Fractal,Fractal,Unit):-
	writeForward(Unit).

writeFractal(_,NotFractal,_):-
	writeln(NotFractal).

writeForward(Unit):-
	write('forward('),
	write(Unit),
	writeln(')').
