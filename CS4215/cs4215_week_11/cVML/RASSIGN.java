package cVML;

public class RASSIGN extends INSTRUCTION {
   public RASSIGN() {
      OPCODE = OPCODES.RASSIGN;
   }
  public String toString() {
      return "RASSIGN";
  }
  public String toXML() {
      return "<rvm:RASSIGN/>";
  }
}
