package cVML;

public class DOT extends INSTRUCTION {
  public DOT() {
     OPCODE = OPCODES.DOT;
  }
  public String toString() {
     return "DOT";
  }
  public String toXML() {
     return "<rvm:DOT/>";
  }
}
