package cPL;

public class IntConstant implements Expression {
    public int value;

    public IntConstant(int v) {
	value = v;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return Integer.toString(value);
    }
}

