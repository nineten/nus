package simPL;

import java.util.*;

import com.sun.xml.internal.ws.wsdl.writer.document.Types;

public class Fun implements Expression {

    public Type funType;
    public Vector<String> formals;
    public Expression body;

    public Fun(Type t, Vector<String> xs, Expression b) {
       funType = t;
       formals = xs;
       body = b;
    }

    public Expression eliminateLet() {
    	return new Fun(funType, formals, body.eliminateLet());
    }
    
    // to be implemented by student
    
    public Type check(TypeEnvironment G) throws TypeError {
    	int i=0;
    	Vector<Type> argTypes = ((FunType)funType).argumentTypes;
		if (argTypes.size() != formals.size())
			throw new TypeError("number of function types do not match: "+this);
		G = G.extend(formals, argTypes);
		Type checked = body.check(G);

		if ( (((FunType)funType).returnType).getClass() != checked.getClass() )
			throw new TypeError("fun return type mismatched: "+this);
		return funType;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      String s = "";
      for (String f : formals)
	  s = s + " " + f;
      return "fun {" + funType + "}" + 
	  s + " -> " + body + " end";
   }
}
