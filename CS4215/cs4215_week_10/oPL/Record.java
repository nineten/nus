package oPL;

import java.util.*;

public class Record implements Expression {

    public Vector<Association> associations;

    public Record(Vector<Association> as) {
	associations = as;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

	public Value eval(Environment e) {
		RecordValue rv = new RecordValue();
		for (Association association : associations) {
			Integer location = Store.theStore.newLocation();
			rv.put(association.property, location);
			// see forum posting: Bi Ran, "bug in lab10"
			Store.theStore.extend(location, null);
			Value v = association.expression.eval(e);
			Store.theStore.set(location, v);
		}
		return rv;
	}

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       Enumeration<Association> en = associations.elements();
       String content = "[";
	   while (en.hasMoreElements()) {
		   content = content + ((Association) en.nextElement());
		   if (en.hasMoreElements()) content = content + ", ";
       }
	   content = content + "]";
       return content;
   }
}
