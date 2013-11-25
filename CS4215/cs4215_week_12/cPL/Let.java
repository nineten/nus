package cPL;

import java.util.*;

public class Let implements Expression {

    public Vector<LetDefinition> definitions;

    public Expression body;

    public Let(Vector<LetDefinition> ds, Expression b) {
	definitions = ds;
	body = b;
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
