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
    // Static Semantics
    // //////////////////////

    public Type check(TypeEnvironment G) throws TypeError {
	if (operator.equals("+") || operator.equals("-") || 
	    operator.equals("*") || operator.equals("/")) {
	    if (argument1.check(G) instanceof IntType && argument2.check(G) instanceof IntType)
		return new IntType();
	    else throw new TypeError("arithmetic operation applied to non-int argument(s) in "+this);
	} else if (operator.equals  (">") || operator.equals ("<") || operator.equals  ("=")) { 
	    if(argument1.check(G) instanceof IntType && argument2.check(G) instanceof IntType)
		return new BoolType();
	    else throw new TypeError("comparison operatation applied to non-int argument(s) in "+this);
	}else if (operator.equals  ("&") || operator.equals   ("|")) {
	    if (argument1.check(G) instanceof BoolType && argument2.check(G) instanceof BoolType)
		return new BoolType();
	    else throw new TypeError("boolean operation applied to non-bool argument(s) in "+this);
	} else throw new TypeError("internal error; non-recognized operator symbol: "+operator);
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

