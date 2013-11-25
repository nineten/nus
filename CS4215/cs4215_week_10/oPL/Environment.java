package oPL;

import java.util.*;

public class Environment extends Hashtable<String,Integer> {

    private static final long serialVersionUID = 7526472295622776147L; 

   // extend corresponds to environment extension.
   // It is important that extend does not change
   // the original environment. Therefore, extend
   // first clones the given environment, makes
   // the binding in the clone and returns the clone.
   public Environment extend(String v, int d) {
      Environment e = (Environment)clone();
      e.put(v,new Integer(d));
      return e;
   }

   public Environment extend(Vector<String> vx, Vector<Integer> vv) {
        Enumeration<String> ex = vx.elements();
        Enumeration<Integer> ev = vv.elements();
        Environment e = this;
        while (ex.hasMoreElements()) 
            e = e.extend( ex.nextElement(), ev.nextElement().intValue());
        return e;
   }

   // For recursive functions, we need an operation
   // that makes a binding in a given environment
   // destructively. That means that the given environment
   // is changed. The environment before the change is
   // lost.
   public void extendDestructive(String v, int d) {
       put(v,new Integer(d));
   }

   public int access(String v) {
       return ((Integer) get(v)).intValue();
   }
}

