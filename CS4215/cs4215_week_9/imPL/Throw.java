package imPL;

public class Throw implements Expression {

    public Expression expression;

    public Throw(Expression e) {
      expression = e;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // no need to implement exception handling

    public StoreAndValue eval(Store s, Environment e) {
	return new StoreAndValue(s,new BoolValue(true));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "throw " + expression + " end";
   }
}
