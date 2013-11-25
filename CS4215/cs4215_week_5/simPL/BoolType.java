package simPL;

import java.util.*;

public class BoolType implements Type {
   public Vector<Type> toVector() {
       Vector<Type> v = new Vector<Type>();
       v.insertElementAt(this,0);
       return v;
   }
   public String toString() {
      return "bool";
   }
   public String toXML() {
      return "<simpl:booltype/>";
   }
}

