package simPL;

import java.util.*;

public class RecFun extends Fun {

    public String funVar;

    public RecFun(String f, Type t, Vector<String> xs, Expression b) {
	super(t,xs,b);
	funVar = f;
    }

    public Expression eliminateLet() {
	return new RecFun(funVar,funType,formals,body.eliminateLet());
    }

    // to be implemented by student
    
    public Type check(TypeEnvironment G) throws TypeError {
//    	System.out.println(body);
//    	System.out.println(funVar);
//    	System.out.println(formals);
//    	System.out.println(((FunType)funType).argumentTypes);
    	G = G.extend(funVar, funType);
    	G = G.extend(formals,((FunType)funType).argumentTypes);
    	Type result = body.check(G);
    	if ( ((FunType)funType).returnType.getClass() != result.getClass() )
    		throw new TypeError("recFun return type mismatched: "+this);
    	return funType;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      String s = "";
      for (String f : formals)
	  s = s + " " + f;
      return "recfun " + funVar + " {" + funType + "} " + 
	  s + " -> " + body + " end";
   }
}
