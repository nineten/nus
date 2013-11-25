package cPL;

public class UnaryPrimitiveApplication implements Expression {

   public String operator;

   public Expression argument;

   public UnaryPrimitiveApplication(String op, Expression a) {
      operator = op;
      argument = a;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return operator + "(" + argument + ")";
   }
}

