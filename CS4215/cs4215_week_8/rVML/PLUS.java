package rVML;

public class PLUS extends INSTRUCTION {
  public PLUS() {
     OPCODE = OPCODES.PLUS;
  }
  public String toString() {
     return "PLUS";
  }
  public String toXML() {
     return "<rvm:PLUS/>";
  }
}
