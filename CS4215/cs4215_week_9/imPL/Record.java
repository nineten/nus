package imPL;

import java.util.*;

public class Record implements Expression {

    public Vector<Association> associations;

    public Record(Vector<Association> as) {
	associations = as;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // no need to implement

    public StoreAndValue eval(Store s, Environment e) {
	return new StoreAndValue(s,new BoolValue(true));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       Enumeration<Association> en = associations.elements();
       String content = "";
       if (en.hasMoreElements()) {
	   Association a = (Association) en.nextElement();
	   content = content + a.property + " : " + a.expression;
	   if (en.hasMoreElements()) 
	       while (en.hasMoreElements()) 
		   content = content + " , " 
		       + ((Association) en.nextElement());
       }
       return "[ " + content + " ]";
   }
}
