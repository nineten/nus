package rePL;

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
    // Support Functions
    // //////////////////////

   public String toString() {
      return " if " + condition + " then " + 
             thenPart + " else " + elsePart + " end";
   }
}
