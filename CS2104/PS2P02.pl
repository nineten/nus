derive(Expression,X,Result) :-	
	Expression =.. [Operator,LeftOp,RightOp],
	derive(LeftOp,X,Result1),
	derive(RightOp,X,Result2),
	decideWhatToDo(Operator,LeftOp,RightOp,Result1,Result2,Result),!.
	
derive(X,X,1).

derive(Expression,_,0) :-
	integer(Expression);
	atom(Expression).
	
decideWhatToDo(+,LeftOp,RightOp,Result1,Result2,Result1 + Result2).
decideWhatToDo(-,LeftOp,RightOp,Result1,Result2,Result1 - Result2).
decideWhatToDo(*,LeftOp,RightOp,Result1,Result2,Result1 * RightOp + LeftOp * Result2).
decideWhatToDo(/,LeftOp,RightOp,Result1,Result2,((Result1 * RightOp - LeftOp * Result2)/(RightOp * RightOp))).
