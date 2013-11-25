package rePL;

import java.util.*;

public class Application implements Expression {

    public Expression operator;

    public Vector<Expression> operands;

    public Application(Expression rator,Vector<Expression> rands) {
	operator = rator; operands = rands;
    }

    public Expression eliminateLet() {
	Vector<Expression> newoperands = new Vector<Expression>();
	for (Expression operand : operands) 
	    newoperands.add(operand.eliminateLet());
	return new Application(operator.eliminateLet(), newoperands);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	String s = "";
	for  (Expression operand : operands) 
	    s = s + " " + operand;
	return "("+operator+" "+s+")";
    }
}
