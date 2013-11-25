package cPL;

import java.util.*;

public class Record implements Expression {

    public Vector<Association> associations;

    public Record(Vector<Association> as) {
	associations = as;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       Enumeration en = associations.elements();
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
