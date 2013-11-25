package simPL;

public class UnaryPrimitiveApplication implements Expression {

   public String operator;

   public Expression argument;

   public UnaryPrimitiveApplication(String op, Expression a) {
      operator = op;
      argument = a;
   }

    // Eliminate the lets in the operand
    public Expression eliminateLet() {
	return new UnaryPrimitiveApplication(operator,
					     argument.eliminateLet());
    }

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return argument.freeVars();
    }

    // Substitute variables for operand
    public Expression substitute(String var, Expression replacement) {
	return 
	    new UnaryPrimitiveApplication(operator, 
					  argument.substitute(var, 
							      replacement));
    }

    // operator must be \ (our only unary operator),
    // and the argument has a redex or is a boolean constant
    public boolean reducible() {
	return 
	    operator . equals( "\\") &&
	    (argument.reducible() || argument instanceof BoolConstant);
    }

    // 
    public Expression oneStep() {
	if (operator . equals( "\\")) 
	    if (argument.reducible()) 
		return new UnaryPrimitiveApplication(operator, 
						     argument.oneStep());
	    else if ((argument instanceof BoolConstant)) 
		// Make sure its not a bogus operator
		return new BoolConstant(!((BoolConstant)argument).value);
	    else     
		// else case will never occur
		return this;
	// else case will never occur
	else return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return operator + "(" + argument + ")";
   }

   public String toXML() {
       return "<simpl:unaryprimitiveapplication>\n" 
	   + "<simpl:operator>" 
	   + operator 
	   + "</simpl:operator>\n"
	   + "<simpl:argument>\n" + argument.toXML() + "</simpl:argument>\n"
	   + "</simpl:unaryprimitiveapplication>\n";
   }
}

