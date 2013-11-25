package cPLcompiler;

import java.util.*;

public class IndexTable extends Vector<String> {
   public void extend(String v) {
      addElement(v);
   }
   public int access(String v) {
      return lastIndexOf(v);
   }
}

