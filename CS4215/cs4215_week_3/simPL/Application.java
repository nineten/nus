package simPL;

import java.util.*;

public class Application implements Expression {

    public Expression operator;

    public Vector<Expression> operands;

    public Application(Expression rator,Vector<Expression> rands) {
	operator = rator; operands = rands;
    }

    // stub; to be replaced by student
    public Expression eliminateLet() {
    	Vector<Expression> new_op = new Vector<Expression>();
    	for (Expression exp : operands)
    	{
    		new_op.add(exp.eliminateLet());
    	}
    	Application new_exp = new Application(operator.eliminateLet(), new_op);
    	
		return new_exp;
    }

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    // stub; to be replaced by student
    public StringSet freeVars() {
		return operator.freeVars();
    }

    // stub; to be replaced by student
    public Expression substitute(String var, Expression replacement) {
    	
    	Vector<Expression> new_op = new Vector<Expression>();
    	for (Expression exp : operands)
    	{
    		new_op.add(exp.substitute(var, replacement));
    	}
    	
    	Application new_exp = new Application(operator.substitute(var, replacement), new_op);
	    return new_exp;
    }

    // stub; to be replaced by student
    public boolean reducible() {
    	for (Expression exp : operands)
    	{
    		if (exp.reducible())
    			return true;
    		if ((exp instanceof IntConstant) || (exp instanceof BoolConstant))
    			return true;
        }
    	
    	return false;
    }

    // stub; to be replaced by student
    public Expression oneStep() {
    	Application new_app = this;
    	Expression new_body;
    	Vector<String> new_formals;
    	int i;
    	if (operator.reducible())
    	{
    		operator = operator.oneStep();
    	}
    	if (operator instanceof Fun)
    	{
    		new_body = ((Fun)operator).body;
    		new_formals = ((Fun)operator).formals;
    		i=0;
	    	for (Expression exp : operands)
	    	{
		    	if ((exp instanceof IntConstant) || (exp instanceof BoolConstant))
		    	{
		    		new_body = new_body.substitute(new_formals.get(i), exp);
		    	}
		    	else if (exp.reducible())
		    	{
		    		exp = exp.oneStep();
		    		new_body = new_body.substitute(new_formals.get(i), exp);
		    	}
		    	else 
		    	{
		    		return exp;
		    	}
		    	i++;
	    	}
	    	return new_body;
    	}
    	
    	return new_app;
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

    public String toXML() {
	String s = "";
	for (Expression operand : operands)
	    s = s + operand.toXML();
	return "<simpl:application>\n"
	    +"<simpl:operator>\n"
	    +operator.toXML()
	    +"</simpl:operator>\n"
	    +"<simpl:arguments>\n"
	    +s
	    +"</simpl:arguments>\n"
	    +"</simpl:application>\n";
    }
}
