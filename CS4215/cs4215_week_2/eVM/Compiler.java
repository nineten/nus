package eVM; 

import ePL.*;
import eVML.*;

import java.util.*;

public class Compiler {

    private Vector <INSTRUCTION> instructions;

    private Expression expression;

    public Compiler(Expression exp) {
	expression = exp;
    }

   // compile

   public INSTRUCTION[] compile() {
      instructions = new Vector <INSTRUCTION> ();
      comp(expression);
      instructions.add(new DONE());
      return instructions.toArray(new INSTRUCTION[1]);
   }

   // comp         

   private void comp(Expression exp) {

      if (exp instanceof UnaryPrimitiveApplication) 
      {
	comp(((UnaryPrimitiveApplication)exp).argument);
        instructions.add(instructionFromOp(((UnaryPrimitiveApplication)exp)
                                           .operator));
      }
      if (exp instanceof BinaryPrimitiveApplication) 
      {
	comp(((BinaryPrimitiveApplication)exp).argument1);
	comp(((BinaryPrimitiveApplication)exp).argument2);
        instructions.add(instructionFromOp(((BinaryPrimitiveApplication)exp)
                                           .operator));
      }
      else if (exp instanceof BoolConstant)
      {
        instructions.add(new 
            LDCB(((BoolConstant)exp).value));
      }
      else if (exp instanceof IntConstant)
      {
        instructions.add(new 
            LDCI(((IntConstant)exp).value));
      }
      else // (exp instanceof NotUsed)
      { 
        // skip
      }
   }

    public static void displayInstructionArray(INSTRUCTION[] is) {
      int i = 0;
      while (! (is[i] == null)) 
         System.out.println(i + " " + is[i++]);
   }

   private static INSTRUCTION instructionFromOp(String op) {
      if      (op.equals("+"))        { return new PLUS(); }
      else if (op.equals("-"))        { return new MINUS(); }
      else if (op.equals("*"))        { return new TIMES(); }
      else if (op.equals("/"))        { return new DIV(); }
      else if (op.equals("\\"))       { return new NOT(); }
      else if (op.equals("&"))        { return new AND(); }
      else if (op.equals("|"))        { return new OR(); }
      else if (op.equals("<"))        { return new LT(); }
      else if (op.equals(">"))        { return new GT(); }
      else /* (op.equals("="))    */  { return new EQ(); }
   }


}
