package oPL;

public class IntConstant implements Expression {
    public int value;

    public IntConstant(int v) {
	value = v;
    }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    public Value eval(Environment e) {
	return new IntValue(value);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return Integer.toString(value);
    }
}

