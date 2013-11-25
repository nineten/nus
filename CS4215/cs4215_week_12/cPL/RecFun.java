package cPL;

import java.util.*;

public class RecFun extends Fun {

    public String funVar;

    public RecFun(String f, Vector<String> xs, Expression b) {
	super(xs,b);
	funVar = f;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      String s = "";
      for (String f : formals)
	  s = s + " " + f;
      return "recfun " + funVar + 
	  s + " -> " + body + " end";
   }
}
