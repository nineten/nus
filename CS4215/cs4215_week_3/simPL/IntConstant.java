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
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return new StringSet();
    }

    public Expression substitute(String var, Expression replacement) {
	return this;
    }

    public boolean reducible() {
	return false;
    }

    public Expression oneStep() {
	return this;
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

