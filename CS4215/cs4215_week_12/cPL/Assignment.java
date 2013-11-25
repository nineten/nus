package cPL;

public class Assignment implements Expression {
    public String leftHandSide;
    public Expression rightHandSide;
   public Assignment(String l, Expression r) {
      leftHandSide = l;
      rightHandSide = r;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(" + leftHandSide + " := " + rightHandSide + ")";
   }
}
