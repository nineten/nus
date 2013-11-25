package imPL;

import java.util.*;

public class Fun implements Expression {

    public Vector<String> formals;
    public Expression body;

    public Fun(Vector<String> xs, Expression b) {
       formals = xs;
       body = b;
    }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // stub to be replaced by proper implementation

    public StoreAndValue eval(Store s, Environment e) {
    	Environment resE = new Environment();
    	StoreAndValue res;
    	int newloc;
    	for (int x=0; x<formals.size(); x++) {
    		newloc = s.newLocation();
    		s = s.extend(newloc, null);
    		resE = resE.extend(formals.get(x), newloc);
    	}
    	
    	res = new StoreAndValue(s, new FunValue(resE, formals, body));
    	return res;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      String s = "";
      for (String f : formals)
	  s = s + " " + f;
      return "fun" + 
	  s + " -> " + body + " end";
   }
}
