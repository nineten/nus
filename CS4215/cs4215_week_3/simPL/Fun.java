package simPL;

import java.util.*;

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

    // //////////////////////
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return body.freeVars().difference(formals);
    }

    public Expression substitute(String var, Expression replacement) {

      StringSet bfv = body.freeVars();            // body free vars
      StringSet rfv = replacement.freeVars();     // replacement free vars
      StringSet ufv = bfv.union(rfv);             // union of the above two

      Vector<String> newformals = new Vector<String>();
      Expression newbody = body;

      // check whether capturing occurs; if it does, get new name and
      // appropriate substitution to the body
      for (String formal : formals) {
	  // if substitution var occurs in list or formal parameters,
	  // there is no free occurrence of var in body: nothing to do
	  if (var.equals(formal)) return this;

	  // if formal parameter is a free variable in the replacement, 
	  // we need to rename formal parameter using substitution
	  if (rfv.contains(formal)) {
	      String newvar = NewName.newName(ufv);
	      ufv.add(newvar);  // making sure that future new names
	                        // are different from newvar
	      newformals.add(newvar);
	      newbody = newbody.substitute(formal,new Variable(newvar));
	  } else {
	      newformals.add(formal);
	  }
      }
      return new Fun(funType, newformals, 
		     newbody.substitute(var, replacement));
    }

    // We never reduce function bodies
    public boolean reducible() {
	return false;
    }

    // oneStep will never be called on recfun
    public Expression oneStep() {
	return this;
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

   public String toXML() {
      String s = "";
      for (String f : formals) 
	  s = s + "<simpl:formal>" + f + "</simpl:formal>";
      return "<simpl:fun>\n" 
	  + "<simpl:funtype>" + funType.toXML() + "</simpl:funtype>\n"
	  + "<simpl:formals>" + s + "</simpl:formals>\n"
	  + "<simpl:body>\n" + body.toXML() + "</simpl:body>\n"
	  + "</simpl:fun>\n";
   }
}
