package cVML;

public class DONE extends INSTRUCTION {
  public DONE() {
     OPCODE = OPCODES.DONE;
  }
  public String toString() {
     return "DONE";
  }
  public String toXML() {
     return "<rvm:DONE/>";
  }
}
