package imPL;

public class Empty implements Expression {

    public Expression expression;

    public Empty(Expression e) {
      expression = e;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // no need to implement

    public StoreAndValue eval(Store s, Environment e) {
	return new StoreAndValue(s,new BoolValue(true));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "( empty " + expression + " )";
   }
}
