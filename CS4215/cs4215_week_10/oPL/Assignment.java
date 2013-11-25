package oPL;

public class Assignment implements Expression {
    public String leftHandSide;
    public Expression rightHandSide;
   public Assignment(String l, Expression r) {
      leftHandSide = l;
      rightHandSide = r;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    public Value eval(Environment e) {
    	Value r = rightHandSide.eval(e);
		Store.theStore.set(e.access(leftHandSide),r);
		return r;
    }
    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(" + leftHandSide + " := " + rightHandSide + ")";
   }
}
