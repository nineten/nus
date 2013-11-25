package simPL;

public class BoolConstant implements Expression {
    public boolean value;
    public BoolConstant(boolean v) {
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
	return (new Boolean(value)).toString();
    }
    public String toXML() {
	return "<simpl:boolconstant>" + ( value ? "true" : "false" )
	    + "</simpl:boolconstant>\n";
    }
}

