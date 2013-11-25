package oPL;

import java.util.*;

public class Application implements Expression {

	public Expression operator;

	public Vector<Expression> operands;

	public Application(Expression rator,Vector<Expression> rands) {
		operator = rator; operands = rands;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	public Value eval(Environment e) {
		Value operatorvalue = operator.eval(e);
		FunValue operatorValue = (FunValue) operatorvalue;
		Environment operatorEnv = operatorValue.environment;
		Vector<Integer> locations = new Vector<Integer>();
		
		for (Expression operand : operands) {
			Value v = operand.eval(e);
			Integer location = Store.theStore.newLocation();
			Store.theStore.extend(location,v);
			locations.add(location);
		}
		return operatorValue.body.eval(operatorEnv.extend(operatorValue.formals,locations));
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
