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
    // Static Semantics
    // //////////////////////

    public Type check(TypeEnvironment G) throws TypeError {
	Type result = argument.check(G);
	if(result instanceof BoolType && operator.equals("\\")) 
	    return new BoolType();
	else throw new TypeError("boolean negation requires an argument of type bool, but argument has type "+result);
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

