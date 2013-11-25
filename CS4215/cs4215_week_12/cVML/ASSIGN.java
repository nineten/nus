package cVML;

public class ASSIGN extends INSTRUCTION {
   public ASSIGN() {
      OPCODE = OPCODES.ASSIGN;
   }
  public String toString() {
      return "ASSIGN";
  }
  public String toXML() {
      return "<rvm:ASSIGN/>";
  }
}
