package imPL;

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

    // stub to be replaced by proper implementation

    public StoreAndValue eval(Store s, Environment e) {
    	Value rhsValue = rightHandSide.eval(s, e).value;
    	s.setElementAt(rhsValue, e.access(leftHandSide));
	return new StoreAndValue(s, rhsValue);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(" + leftHandSide + " := " + rightHandSide + ")";
   }
}
