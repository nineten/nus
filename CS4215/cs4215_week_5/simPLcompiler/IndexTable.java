package simPLcompiler;

import java.util.*;

public class IndexTable extends Vector<String> {

    private static final long serialVersionUID = 7526472295622776147L; 

   public void extend(String v) {
      addElement(v);
   }
   public int access(String v) {
      return lastIndexOf(v);
   }
}

