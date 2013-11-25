package eVML;

public class LT extends INSTRUCTION {
  public LT() {
     OPCODE = OPCODES.LT;
  }
  public String toString() {
     return "LT";
  }
  public String toXML() {
     return "<evml:LT/>";
  }
}
