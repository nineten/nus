package imPL;

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

    public StoreAndValue eval(Store s, Environment e) {
    	    StoreAndValue s_and_v = argument.eval(s, e);
    	    BoolValue b = (BoolValue) s_and_v.value;
    		return new StoreAndValue(s_and_v.store,new BoolValue(!b.value));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return operator + "(" + argument + ")";
   }
}

