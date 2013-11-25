package simPL;

public class BinaryPrimitiveApplication implements Expression {
    public String operator;
    public Expression argument1,argument2;

    public BinaryPrimitiveApplication(String op, Expression a1, Expression a2) {
	operator = op;
	argument1 = a1;
	argument2 = a2;
    }

    // Eliminate let for the operands
    public Expression eliminateLet() {
	return new BinaryPrimitiveApplication(operator, 
					      argument1.eliminateLet(), 
					      argument2.eliminateLet());
    }

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return argument1.freeVars().union(argument2.freeVars());
    }

    // Substitute variables for the operands
    public Expression substitute(String var, Expression newexp) {
	return 
	    new BinaryPrimitiveApplication(operator, 
					   argument1.substitute(var,newexp),
					   argument2.substitute(var,newexp));
    }

    // 
    public boolean reducible() {
      return 
	  // Case 1:
	  // proper simPL operator, and arg1 has redex 
	  //                            or arg1 is value and arg2 has redex
	  (operator . equals( "+") || operator . equals( "-") || 
	   operator . equals( "*") || operator . equals( "/") ||
	   operator . equals( "=") || operator . equals( ">") || 
	   operator . equals( "<") || 
	   operator . equals( "&") || operator . equals( "|")) &&  
	  (argument1.reducible()            // first argument has redex
	   ||                            
	   IsValue.isValue(argument1) &&  // first arg is value, and
	   argument2.reducible())          // second arg has redex

	  ||   

	  // Case 2:
	  // boolean operator applies
	  (operator . equals( "&") || operator . equals( "|")) &&  // operator is boolean
	  argument1 instanceof BoolConstant &&     // both are boolean 
	  argument2 instanceof BoolConstant        // values and the

	  ||

	  // Case 3:
	  // integer operator applies (no division)
	  (operator . equals( "+") || operator . equals( "-") ||   // operator (no div)
	   operator . equals( "*") || operator . equals( "=") ||   // requires integers
	   operator . equals( ">") || operator . equals( "<")) &&  // and 
	  argument1 instanceof IntConstant &&      // both are integers
	  argument2 instanceof IntConstant     

	  ||

	  // Case 4:
	  // division applies
	  operator . equals( "/") &&                       // operator is / 
	  argument1 instanceof IntConstant &&      // both are integers,
	  argument2 instanceof IntConstant &&      // and
	  ((IntConstant) argument2).value != 0;    // second arg is not 0
    }

    public Expression oneStep() {
	// Case 1:
	// proper simPL operator, and arg1 has redex 
	//                            or arg1 is value and arg2 has redex
	if ((operator . equals( "+") || operator . equals( "-") || 
	     operator . equals( "*") || operator . equals( "/") ||
	     operator . equals( "=") || operator . equals( ">") || 
	     operator . equals( "<") || 
	     operator . equals( "&") || operator . equals( "|")) &&
	    (argument1.reducible() ||
	     (IsValue.isValue(argument1) && argument2.reducible())))     
	    if (argument1.reducible())
		// Reduce first argument
		return new BinaryPrimitiveApplication(operator, 
						      argument1.oneStep(), 
						      argument2);
	    else // (IsValue.isValue(argument1) && argument2.hasRedex())     
		return new BinaryPrimitiveApplication(operator, 
						      argument1, 
						      argument2.oneStep());

	// Case 2:
	// boolean operator applies
	else if ((operator . equals( "&") || operator . equals( "|")) &&
		 argument1 instanceof BoolConstant && 
		 argument2 instanceof BoolConstant) {
	    BoolConstant x1 = (BoolConstant)argument1;
	    BoolConstant x2 = (BoolConstant)argument2;
	    if (operator . equals( "&")) 
		return new BoolConstant(x1.value && x2.value);
	    else // (operator . equals( "|")) 
		return new BoolConstant(x1.value || x2.value);
	}

	// Case 3:
	// integer operator applies (no division)
	else if ((operator . equals( "+") || operator . equals( "-") ||   // operator (no div)
		  operator . equals( "*") || operator . equals( "=") ||   // requires integers
		  operator . equals( ">") || operator . equals( "<")) &&  // and 
		 argument1 instanceof IntConstant &&      // operators are
		 argument2 instanceof IntConstant) {      // integers
	    IntConstant x1 = (IntConstant)argument1;
	    IntConstant x2 = (IntConstant)argument2;
	    if (operator . equals( "+")) 
		return new IntConstant(x1.value + x2.value);
	    else if (operator . equals( "-")) 
		return new IntConstant(x1.value - x2.value);
	    else if (operator . equals( "*")) 
		return new IntConstant(x1.value * x2.value);
	    else if (operator . equals( "=")) 
		return new BoolConstant(x1.value == x2.value);
	    else if (operator . equals( ">")) 
		return new BoolConstant(x1.value > x2.value);
	    else if (operator . equals( "<")) 
		return new BoolConstant(x1.value < x2.value);
	    else return this;
	}

	// Case 4:
	// division applies
	else if (operator . equals( "/") &&
		 argument1 instanceof IntConstant && 
		 argument2 instanceof IntConstant &&
		 ((IntConstant)argument2).value != 0)
	    return new IntConstant(((IntConstant)argument1).value /
				   ((IntConstant)argument2).value);

	// this case will never occur
	else return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return "(" + argument1 + " " + operator + " " + argument2 + ")";
    }

   public String toXML() {
       return "<simpl:binaryprimitiveapplication>\n" 
	   + "<simpl:operator>" 
	   + ( (operator.equals(">")) 
	       ? "&gt;"
	       : (operator.equals("<")) 
	       ? "&lt;"
	       : (operator.equals("&"))
	       ? "&amp;"
	       :
	       operator )
	   + "</simpl:operator>\n"
	   + "<simpl:argument1>\n" + argument1.toXML() + "</simpl:argument1>\n" 
	   + "<simpl:argument2>\n" + argument2.toXML() + "</simpl:argument2>\n" 
	   + "</simpl:binaryprimitiveapplication>\n";
   }
}

