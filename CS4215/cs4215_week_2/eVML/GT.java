package eVML;

public class GT extends INSTRUCTION {
  public GT() {
     OPCODE = OPCODES.GT;
  }
  public String toString() {
     return "GT";
  }
  public String toXML() {
     return "<evml:GT/>";
  }
}
