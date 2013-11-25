package eVML;

public class NOT extends INSTRUCTION {
  public NOT() {
     OPCODE = OPCODES.NOT;
  }
  public String toString() {
     return "NOT";
  }
  public String toXML() {
     return "<evml:NOT/>";
  }
}
