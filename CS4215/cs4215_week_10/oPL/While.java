package oPL;

public class While implements Expression {
    public Expression test, body;
   public While(Expression t, Expression b) {
      test = t;
      body = b;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // stub to be replaced by proper implementation

    public Value eval(Environment e) {
    	while ( ((BoolValue) test.eval(e)).value) {
    		body.eval(e);
    	}
    	return new BoolValue(true);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(while" + test + " do " + body + ")";
   }
}
