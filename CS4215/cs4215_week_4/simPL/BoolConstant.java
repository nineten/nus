package simPL;

public class BoolConstant implements Expression {
	public boolean value;
	public BoolConstant(boolean v) {
		value = v;
	}

	public Expression eliminateLet() {
		return this;
	}

	public Type check(TypeEnvironment G) throws TypeError {
		return new BoolType();
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return (new Boolean(value)).toString();
	}
}

