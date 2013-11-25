package cPL;

import java.util.*;

public class Application implements Expression {

    public Expression operator;

    public Vector<Expression> operands;

    public Application(Expression rator,Vector<Expression> rands) {
	operator = rator; operands = rands;
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
