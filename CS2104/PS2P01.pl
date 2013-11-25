count(Expression,Operator,Result) :- 
	Expression =.. [Operator,LeftOp,RightOp],
	count(LeftOp,Operator,Result1),
	count(RightOp,Operator,Result2),
	Result is Result1 + Result2 + 1,!.
	
count(Expression,Operator,Result) :-
	(Expression =.. [Operator2,LeftOp,RightOp];Expression =.. [LeftOp,RightOp]),
	count(LeftOp,Operator,Result1), 
	count(RightOp,Operator,Result2),
	Result is Result1 + Result2,!.
	
count(Expression,_,0) :-
	integer(Expression);
	atom(Expression).
