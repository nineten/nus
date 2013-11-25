package cPL;

public class RecordAssignment implements Expression {
    public Expression recordExpression;
    public Expression property;
    public Expression rightHandSide;
    public RecordAssignment(Expression rec, Expression p, Expression rh) {
	recordExpression = rec;
	property = p;
	rightHandSide = rh;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "((" + recordExpression + ")."  + property 
	   + " := " + rightHandSide + ")";
   }
}
