package imPL;

public class If implements Expression {

   public Expression condition,thenPart,elsePart;

   public If(Expression c, Expression t, Expression e) {
      condition = c;
      thenPart = t;
      elsePart = e;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    public StoreAndValue eval(Store s, Environment e) {
    		StoreAndValue s_and_v = condition.eval(s,e);
    		BoolValue cond = (BoolValue) s_and_v.value;
    		if (cond.value) {
    			return thenPart.eval(s_and_v.store, e);
    		} else {
    			return elsePart.eval(s_and_v.store, e);
    		}
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      return " if " + condition + " then " + 
             thenPart + " else " + elsePart + " end";
   }
}
