package cPL;

public class ThreadExpression implements Expression {

    public Expression expression;

    public ThreadExpression(Expression e) {
      expression = e;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "thread " + expression + " end";
   }
}
