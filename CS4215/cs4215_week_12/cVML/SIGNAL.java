package cVML;

public class SIGNAL extends INSTRUCTION {
   public int INDEX;
   public SIGNAL(int i) {
      OPCODE = OPCODES.SIGNAL;
      INDEX = i;
   }
  public String toString() {
     return "SIGNAL" + " " + INDEX;
  }
  public String toXML() {
     return "<cvml:SIGNAL>"+ INDEX + "</cvml:SIGNAL>";
  }
}
