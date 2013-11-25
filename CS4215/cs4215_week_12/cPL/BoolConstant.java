package cPL;

public class BoolConstant implements Expression {
    public boolean value;
    public BoolConstant(boolean v) {
	value = v;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return (new Boolean(value)).toString();
    }
}

