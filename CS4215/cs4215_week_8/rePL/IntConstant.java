package rePL;

public class IntConstant implements Expression {
    public int value;

    public IntConstant(int v) {
	value = v;
    }

    public Expression eliminateLet() {
	return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return Integer.toString(value);
    }
}

