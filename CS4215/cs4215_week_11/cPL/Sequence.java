package cPL;

public class Sequence implements Expression {
    public Expression firstPart, secondPart;
   public Sequence(Expression f, Expression s) {
      firstPart = f;
      secondPart = s;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(" + firstPart + " ; " + secondPart + ")";
   }
}
