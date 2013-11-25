package simPL;

public class Variable implements Expression {

   public String varname;

   public Variable(String nam) {
      varname = nam;
   }

    public Expression eliminateLet() {
	return this;
    }

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return new StringSet(varname);
    }

    public Expression substitute(String var, Expression replacement) {
	// If the variable to replace is this variable, then return replacement
	// Else nothing to do
	if (var.equals(varname)) 
	    return replacement;
	else return this;
    }

    public boolean reducible() {
	return false;
    }

    // will never be called
    public Expression oneStep() {
	return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return varname;
    }

    public String toXML() {
	return "<simpl:variable>" + varname + "</simpl:variable>\n";
    }
}

