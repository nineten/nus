package cPL;

public class Throw implements Expression {

    public Expression expression;

    public Throw(Expression e) {
      expression = e;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "throw " + expression + " end";
   }
}
