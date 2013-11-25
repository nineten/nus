package sVML;

public class GOTO extends INSTRUCTION {

   public int ADDRESS;

   public GOTO() {
     OPCODE = OPCODES.GOTO;
     ADDRESS = 0;
   }

   public String toString() {
      return "GOTO" + " " + ADDRESS;
   }
  public String toXML() {
     return "<svm:GOTO/>";
  }
}
