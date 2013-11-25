package oPL;

public class UnaryPrimitiveApplication implements Expression {

   public String operator;

   public Expression argument;

   public UnaryPrimitiveApplication(String op, Expression a) {
      operator = op;
      argument = a;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////
	public Value eval(Environment e) {
		Value x = argument.eval(e);

		if (operator.equals("\\")) {
			return new BoolValue(!((BoolValue)x).value);
		} else {
			return new BoolValue(((RecordValue)x).isEmpty());
		}
	}


    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return operator + "(" + argument + ")";
   }
}

