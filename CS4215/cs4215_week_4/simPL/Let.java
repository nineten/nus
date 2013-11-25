package simPL;

import java.util.*;

public class Let implements Expression {

    public Vector<LetDefinition> definitions;

    public Type bodyType;

    public Expression body;

    public Let(Vector<LetDefinition> ds, Type bt, Expression b) {
	definitions = ds;
	bodyType = bt;
	body = b;
    }

    public Expression eliminateLet() {
      Vector<Expression> oper = new Vector<Expression>();
      Vector<String> formals = new Vector<String>();
      Vector<Type> argtype = new Vector<Type>();

      // Build the various vectors required for the application object
      for (LetDefinition d : definitions) {
	  oper.add(d.rightHandExpression.eliminateLet());
	  formals.add(d.variable);
	  argtype.add(d.type);
      }
      // Build the function object
      FunType funtype = new FunType(argtype, bodyType);
      Fun func = new Fun(funtype, formals, body.eliminateLet());

      return new Application(func, oper);
    }
    
    // let is supposed to be eliminated already
    // If it isn't, there is a problem with eliminateLet
    public Type check(TypeEnvironment G) throws TypeError {
	throw new Error("Internal error: eliminateLet misses Let: "+this);
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       String s = "";
       for (LetDefinition d : definitions)
	   s = s + " " + d;
       return "let " + s + " in { " + bodyType + " } " + body + " end";
   }
}
