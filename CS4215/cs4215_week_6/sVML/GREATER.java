package sVML;

public class GREATER extends INSTRUCTION {
  public GREATER() {
     OPCODE = OPCODES.GREATER;
  }
  public String toString() {
     return "GREATER";
  }
  public String toXML() {
     return "<svm:GREATER/>";
  }
}
