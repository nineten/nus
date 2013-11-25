package rePL;

public class Hasproperty implements Expression {

    public Expression expression;
    public String property;

    public Hasproperty(Expression e, String p) {
      expression = e;
      property = p;
   }

    public Expression eliminateLet() {
	return new Hasproperty(expression.eliminateLet(),property);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "( " + expression + " hasproperty " + property + " )";
   }

}
