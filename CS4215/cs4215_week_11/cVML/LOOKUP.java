package cVML;

public class LOOKUP extends INSTRUCTION {
  public LOOKUP() {
     OPCODE = OPCODES.LOOKUP;
  }
  public String toString() {
     return "LOOKUP";
  }
  public String toXML() {
     return "<rvm:LOOKUP/>";
  }
}
