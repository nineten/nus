package simPL;

import java.util.*;

public class IntType implements Type {

   public Vector<Type> toVector() {
       Vector<Type> v = new Vector<Type>();
       v.insertElementAt(this,0);
       return v;
   }

   public String toString() {
      return "int";
   }
}

