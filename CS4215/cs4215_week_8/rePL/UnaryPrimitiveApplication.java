package rePL;

public class UnaryPrimitiveApplication implements Expression {

   public String operator;

   public Expression argument;

   public UnaryPrimitiveApplication(String op, Expression a) {
      operator = op;
      argument = a;
   }

    // Eliminate the lets in the operand
    public Expression eliminateLet() {
	return new UnaryPrimitiveApplication(operator,
					     argument.eliminateLet());
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return operator + "(" + argument + ")";
   }
}

