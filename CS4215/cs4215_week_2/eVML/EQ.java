package eVML;

public class EQ extends INSTRUCTION {
  public EQ() {
     OPCODE = OPCODES.EQ;
  }
  public String toString() {
     return "EQ";
  }
  public String toXML() {
     return "<evml:EQ/>";
  }
}
