package simPL;

public class IntConstant implements Expression {
    public int value;

    public IntConstant(int v) {
	value = v;
    }

    public Expression eliminateLet() {
	return this;
    }

    public Type check(TypeEnvironment G) throws TypeError {
		return new IntType();
    }
	
    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return Integer.toString(value);
    }
}

