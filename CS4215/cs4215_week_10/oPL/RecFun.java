package oPL;

import java.util.*;

public class RecFun extends Fun {

    public String funVar;

    public RecFun(String f, Vector<String> xs, Expression b) {
	super(xs,b);
	funVar = f;
    }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////
    
    public Value eval(Environment e) {
    	Integer location = Store.theStore.newLocation();
    FunValue v = (FunValue) super.eval(e.extend(funVar,location));
    	Store.theStore.extend(location,v);
    	return v;
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
