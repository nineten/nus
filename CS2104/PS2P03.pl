sim(Expression,Result) :-
	Expression =.. [Operator,LeftOp,RightOp],
	sim(LeftOp,Rleft),
	sim(RightOp,Rright),
	resolve(Operator,Rleft,Rright,Result),!.
	
resolve(+,Rleft,Rright,Result) :-
	integer(Rleft),
	integer(Rright),
	Result is Rleft + Rright;
	Result =.. [+,Rleft,Rright].
	
resolve(-,Rleft,Rright,Result) :-
	integer(Rleft),
	integer(Rright),
	Result is Rleft - Rright;
	Result =.. [-,Rleft,Rright].
	
resolve(*,Rleft,Rright,Result) :-
	integer(Rleft),
	integer(Rright),
	Result is Rleft * Rright;
	Result =.. [*,Rleft,Rright].
	
resolve(/,Rleft,Rright,Result) :-
	integer(Rleft),
	integer(Rright),
	Result is Rleft / Rright;
	Result =.. [/,Rleft,Rright].
	
sim(Expression,Result) :-
	integer(Expression),
	Result is Expression.
	
sim(Expression,Expression) :-
	atom(Expression).
