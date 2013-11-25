package imPL;

public class Try implements Expression {
    public String exceptionVar;
    public Expression tryExpression;
    public Expression withExpression;
    public Try(Expression t, String ev, Expression w) {
	tryExpression = t;
	exceptionVar = ev;
	withExpression = w;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // no need to implement exception handling here

    public StoreAndValue eval(Store s, Environment e) {
	    return new StoreAndValue(s,new BoolValue(true));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      return "try " + tryExpression 
	  + " catch " + exceptionVar 
	  + " with "  + withExpression + " end";
   }
}
