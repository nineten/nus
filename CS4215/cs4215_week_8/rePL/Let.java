package rePL;

import java.util.*;

public class Let implements Expression {

    public Vector<LetDefinition> definitions;

    public Expression body;

    public Let(Vector<LetDefinition> ds, Expression b) {
	definitions = ds;
	body = b;
    }

    public Expression eliminateLet() {
      Vector<Expression> oper = new Vector<Expression>();
      Vector<String> formals = new Vector<String>();

      // Build the various vectors required for the application object
      for (LetDefinition d : definitions) {
	  oper.add(d.rightHandExpression.eliminateLet());
	  formals.add(d.variable);
      }
      // Build the function object
      Fun func = new Fun(formals, body.eliminateLet());

      return new Application(func, oper);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       String s = "";
       for (LetDefinition d : definitions)
	   s = s + " " + d;
       return "let " + s + " in " + body + " end";
   }
}
