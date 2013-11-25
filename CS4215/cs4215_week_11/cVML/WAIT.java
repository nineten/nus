package cVML;

public class WAIT extends INSTRUCTION {
   public int INDEX;
   public WAIT(int i) {
      OPCODE = OPCODES.WAIT;
      INDEX = i;
   }
  public String toString() {
     return "WAIT" + " " + INDEX;
  }
  public String toXML() {
     return "<cvml:WAIT>"+ INDEX + "</cvml:WAIT>";
  }
}
