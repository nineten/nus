package eVML;

public class DIV extends INSTRUCTION {
  public DIV() {
     OPCODE = OPCODES.DIV;
  }
  public String toString() {
     return "DIV";
  }
  public String toXML() {
     return "<evml:DIV/>";
  }
}
