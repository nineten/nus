package cPL;

public class While implements Expression {
    public Expression test, body;
   public While(Expression t, Expression b) {
      test = t;
      body = b;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(while" + test + " do " + body + ")";
   }
}
