package rePL;

public class Throw implements Expression {

    public Expression expression;

    public Throw(Expression e) {
      expression = e;
   }

    public Expression eliminateLet() {
	return new Throw(expression.eliminateLet());
    }

   public String toString() {
       return "throw " + expression + " end";
   }
}
