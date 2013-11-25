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

    // to be implemented by student

    public Type check(TypeEnvironment G) throws TypeError {
		Type condT = condition.check(G);
		Type thenT = thenPart.check(G);
		Type elseT = elsePart.check(G);
		if(condT instanceof BoolType)
		{
			if ((thenT instanceof IntType) && (elseT instanceof IntType))
				return new IntType();
			else if ((thenT instanceof BoolType) && (elseT instanceof BoolType))
				return new BoolType();
			else throw new TypeError("outcome of conditionals mismatched: "+this);
		}
		else throw new TypeError("conditional not bool type: "+this);
    }
    
    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      return " if " + condition + " then " + 
             thenPart + " else " + elsePart + " end";
   }
}
