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
    // Dynamic Semantics
    // //////////////////////

    public StringSet freeVars() {
	return super.freeVars().difference(formals);
    }

    public Expression substitute(String var, Expression replacement) {

      // if function variable is the same as substitution variable
      // there cannot be any free occurrence; return expression unchanged
      if (var.equals(funVar)) return this; 

      StringSet bfv = body.freeVars();            // body free vars
      StringSet rfv = replacement.freeVars();     // replacement free vars
      StringSet ufv = bfv.union(rfv);             // union of the above two
      ufv.add(funVar);

      String newfunVar;
      Expression newbody = body;

      // if function name is a free variable in the replacement, 
      // we need to rename function
      if (rfv.contains(funVar)) {
	  newfunVar = NewName.newName(ufv);
	  ufv.add(newfunVar); // make sure that other renamings come up with
	                      // a name different from newfunVar
	  newbody = newbody.substitute(funVar, new Variable(newfunVar));
      } else {
	  newfunVar = funVar;
      }

      Vector<String> newformals = new Vector<String>();

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
	      newbody = newbody.substitute(formal, new Variable(newvar));
	  } else 
	      newformals.add(formal);
      }

      return new RecFun(newfunVar, funType, newformals, 
			newbody.substitute(var, replacement));
    }

    // We never reduce functions before they are called
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
