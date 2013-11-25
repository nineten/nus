package simPL;

import java.util.*;

public class TypeEnvironment extends Hashtable<String,Type> {

	private static final long serialVersionUID = 1L;

	public TypeEnvironment extend(String v, Type t) {
        TypeEnvironment e = (TypeEnvironment)clone();
        e.put(v,t);
        return e;
    }

    public TypeEnvironment extend(Vector<String> vs, Vector<Type> ts) {
        Enumeration<String> ev = vs.elements();
        Enumeration<Type> et = ts.elements();
	TypeEnvironment e = this;
        while (ev.hasMoreElements()) 
            e = e.extend( ev.nextElement(), et.nextElement());
        return e;
    }

   public Type access(String v) {
      return get(v);
   }
}

