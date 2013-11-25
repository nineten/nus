package rePL;

public class Dot implements Expression {

    public Expression expression;

    public String property;

    public Dot(Expression e, String p) {
      expression = e;
      property = p;
   }

    // Eliminate let for the operands
    public Expression eliminateLet() {
	return new Dot(expression.eliminateLet(),property);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "( " + expression + " . " + property + " )";
   }
}
