package rVML;

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
     return "<rvm:GOTO>" + ADDRESS + "</rvm:GOTO>";
  }
}
