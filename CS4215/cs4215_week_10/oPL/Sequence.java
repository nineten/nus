package oPL;

public class Sequence implements Expression {
    public Expression firstPart, secondPart;
   public Sequence(Expression f, Expression s) {
      firstPart = f;
      secondPart = s;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // stub to be replaced by proper implementation

    public Value eval(Environment e) {
    	firstPart.eval(e);
    	return secondPart.eval(e);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(" + firstPart + " ; " + secondPart + ")";
   }
}
