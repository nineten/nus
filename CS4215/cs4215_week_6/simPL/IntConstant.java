package simPL;

public class IntConstant implements Expression {
    public int value;

    public IntConstant(int v) {
	value = v;
    }

    public Expression eliminateLet() {
	return this;
    }

    // //////////////////////
    // Static Semantics
    // //////////////////////

    public Type check(TypeEnvironment G) throws TypeError {
		return new IntType();
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return Integer.toString(value);
    }

    public String toXML() {
	return "<simpl:intconstant>" 
	    + Integer.toString(value) + "</simpl:intconstant>\n";
    }
}

