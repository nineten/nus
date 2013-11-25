package cVML;

public class GOTO extends INSTRUCTION {

   public int ADDRESS;

   public GOTO() {
     OPCODE = OPCODES.GOTO;
     ADDRESS = 0;
   }

   public GOTO(int a) {
     OPCODE = OPCODES.GOTO;
     ADDRESS = a;
   }

   public String toString() {
      return "GOTO" + " " + ADDRESS;
   }
  public String toXML() {
     return "<rvm:GOTO>" + ADDRESS + "</rvm:GOTO>";
  }
}
