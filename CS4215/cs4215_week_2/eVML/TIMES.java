package eVML;

public class TIMES extends INSTRUCTION {
  public TIMES() {
     OPCODE = OPCODES.TIMES;
  }
  public String toString() {
     return "TIMES";
  }
  public String toXML() {
     return "<evml:TIMES/>";
  }
}
