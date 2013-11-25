package simPLdynamic;

import simPL.*;

class Evaluator{
  
    public static Expression evaluate(Expression e_with_lets) {
        return eval(e_with_lets.eliminateLet());
    }

    // eval implements the evaluation relation
    // by repeatedly calling oneStep until the expression
    // is no longer reducible
    static public Expression eval(Expression exp)  {
	return 
	    exp.reducible() ? 
	    eval(exp.oneStep())
	    : exp;
    }
}

