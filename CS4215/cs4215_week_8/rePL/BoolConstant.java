package rePL;

public class BoolConstant implements Expression {
    public boolean value;
    public BoolConstant(boolean v) {
	value = v;
    }

    public Expression eliminateLet() {
	return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return (new Boolean(value)).toString();
    }
}

