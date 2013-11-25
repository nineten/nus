package rePL;

public class Empty implements Expression {

    public Expression expression;

    public Empty(Expression e) {
      expression = e;
   }

    public Expression eliminateLet() {
	return new Empty(expression.eliminateLet());
    }

   public String toString() {
       return "( empty " + expression + " )";
   }
}
