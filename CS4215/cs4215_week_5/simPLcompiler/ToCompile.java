package simPLcompiler;

import simPL.*;

import sVML.*;

public class ToCompile {
   public LDF funInstruction;
   public IndexTable indexTable;
   public Expression body;
    public String funVar;

   public ToCompile(LDF fi, IndexTable it, Expression b) {
      funInstruction = fi;
      indexTable = it;
      body = b;
      funVar = "";
   }
   public ToCompile(LDF fi, IndexTable it, Expression b, String fv) {
      funInstruction = fi;
      indexTable = it;
      body = b;
      funVar = fv;
   }
}

