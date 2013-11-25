package sVML;

public class EQUAL extends INSTRUCTION {
  public EQUAL() {
     OPCODE = OPCODES.EQUAL;
  }
  public String toString() {
     return "EQUAL";
  }
  public String toXML() {
     return "<svm:EQUAL/>";
  }
}
