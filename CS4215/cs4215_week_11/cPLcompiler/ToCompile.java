package cPLcompiler;

import cPL.*;
import cVML.*;

import java.util.*;

public class ToCompile {
   public ADDRESS_INSTRUCTION addressInstruction;
   public IndexTable indexTable;
   public Expression body;
    public String funVar;
    public INSTRUCTION toInsert;

    public ToCompile(ADDRESS_INSTRUCTION ai, 
		     IndexTable it, Expression b, String fv, INSTRUCTION ti) {
      addressInstruction = ai;
      indexTable = it;
      body = b;
      funVar = fv;
      toInsert = ti;
   }
}

