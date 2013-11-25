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

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
      // Nothing to do here, shouldn't ever be called
      return new StringSet();
    }

    public Expression substitute(String var, Expression replacement) {
      // Nothing to do here, shouldn't ever be called
      return this;
    }

    public boolean reducible() {
      // Nothing to do here, shouldn't ever be called
      return false;
    }

    public Expression oneStep() {
      // Nothing to do here, shouldn't ever be called
      return eliminateLet();
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

   public String toXML() {
       String s = "";
       for (LetDefinition d : definitions) 
	   s = s + d.toXML();
       return "<simpl:let>\n" 
	   + "<simpl:definitions>\n"
	   + s 
	   + "</simpl:definitions>\n"
	   + "<simpl:bodytype>" 
	   + bodyType.toXML()
	   + "</simpl:bodytype>\n<simpl:body>" + body.toXML() 
	   + "</simpl:body>\n"
	   + "</simpl:let>\n";
   }

}
