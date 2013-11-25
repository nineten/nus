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

    // //////////////////////
    // Static Semantics
    // //////////////////////

    public Type check(TypeEnvironment G) throws TypeError {
       	if (formals.contains(funVar)) {
    	    throw new TypeError("function variable "+funVar+" also appears as parameter");
    	}
    	return super.check(G.extend(funVar,funType));
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

   public String toXML() {
      String s = "";
      for (String f : formals)
	  s = s + "<simpl:formal>" + f + "</simpl:formal>";
      return "<simpl:recfun>\n" 
	  + "<simpl:funvar>" + funVar + "</simpl:funvar>\n"
	  + "<simpl:funtype>" + funType.toXML() + "</simpl:funtype>\n"
	  + "<simpl:formals>" + s + "</simpl:formals>\n"
	  + "<simpl:body>\n" + body.toXML() + "</simpl:body>\n"
	  + "</simpl:recfun>\n";
   }

}
