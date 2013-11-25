package simPL;

public class If implements Expression {

   public Expression condition,thenPart,elsePart;

   public If(Expression c, Expression t, Expression e) {
      condition = c;
      thenPart = t;
      elsePart = e;
   }

    // Eliminate let in the condition, then part and else part
    public Expression eliminateLet() {
	return new If(condition.eliminateLet(), 
		      thenPart.eliminateLet(), 
		      elsePart.eliminateLet());
    }

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return 
	    condition.freeVars()
	    .union(thenPart.freeVars())
	    .union(elsePart.freeVars());
    }

    // Substitute for the condition, then part and else part
    public Expression substitute(String var, Expression replacement) {
	return new If(condition.substitute(var, replacement), 
		      thenPart.substitute(var, replacement), 
		      elsePart.substitute(var, replacement));
    }

    // Either the condition can be reduced, or it is a BoolConstant
    public boolean reducible() {
	return condition.reducible() || condition instanceof BoolConstant;
    }

    // if the condition is reducible, reduce it
    // if the condition is boolean constant, return the corresponding 
    // then/else part
    public Expression oneStep() {
      if (condition.reducible()) 
	  return new If(condition.oneStep(), thenPart, elsePart);
      else 
	  if (condition instanceof BoolConstant)  
	      if (((BoolConstant)condition).value) 
		  return thenPart;
	      else return elsePart;
	  else return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      return " if " + condition + " then " + 
             thenPart + " else " + elsePart + " end";
   }

   public String toXML() {
      return "<simpl:if>\n"
	  + "<simpl:condition>\n" + condition.toXML() + "</simpl:condition>\n"
	  + "<simpl:thenpart>\n" + thenPart.toXML() + "</simpl:thenpart>\n"
	  + "<simpl:elsepart>\n" + elsePart.toXML() + "</simpl:elsepart>\n"
	  + "</simpl:if>\n";
   }
}
