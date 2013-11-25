package rVML;

public class HASP extends INSTRUCTION {
  public HASP() {
     OPCODE = OPCODES.HASP;
  }
  public String toString() {
     return "HASP";
  }
  public String toXML() {
     return "<rvm:HASP/>";
  }
}
