package rVML;

public class LESS extends INSTRUCTION {
  public LESS() {
     OPCODE = OPCODES.LESS;
  }
  public String toString() {
     return "LESS";
  }
  public String toXML() {
     return "<rvm:LESS/>";
  }
}
