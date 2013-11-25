package eVML;

public class OR extends INSTRUCTION {
  public OR() {
     OPCODE = OPCODES.OR;
  }
  public String toString() {
     return "OR";
  }
  public String toXML() {
     return "<evml:OR/>";
  }
}
