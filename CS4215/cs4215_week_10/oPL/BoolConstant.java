package oPL;

public class BoolConstant implements Expression {
	public boolean value;
	public BoolConstant(boolean v) {
		value = v;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	public Value eval(Environment e) {
		return new BoolValue(value);
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return (new Boolean(value)).toString();
	}
}

