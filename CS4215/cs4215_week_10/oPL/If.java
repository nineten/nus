package oPL;

public class If implements Expression {

	public Expression condition,thenPart,elsePart;

	public If(Expression c, Expression t, Expression e) {
		condition = c;
		thenPart = t;
		elsePart = e;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	public Value eval(Environment e) {
		Value tmp = condition.eval(e);

		if (((BoolValue) tmp).value)
			return thenPart.eval(e);
		else
			return elsePart.eval(e);
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return " if " + condition + " then " + 
		thenPart + " else " + elsePart + " end";
	}
}
