package sVML;

public class RTN extends INSTRUCTION {
  public RTN() {
     OPCODE = OPCODES.RTN;
  }
  public String toString() {
     return "RTN";
  }
  public String toXML() {
     return "<svm:RTN/>";
  }
}
