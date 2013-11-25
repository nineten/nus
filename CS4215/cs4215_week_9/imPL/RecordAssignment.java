package imPL;

public class RecordAssignment implements Expression {
    public Expression recordExpression;
    public PropertyConstant property;
    public Expression rightHandSide;
    public RecordAssignment(Expression rec, PropertyConstant p, Expression rh) {
	recordExpression = rec;
	property = p;
	rightHandSide = rh;
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
       return "((" + recordExpression + ")."  + property 
	   + " := " + rightHandSide + ")";
   }
}
