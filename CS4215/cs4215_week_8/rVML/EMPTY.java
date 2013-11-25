package rVML;

public class EMPTY extends INSTRUCTION {
  public EMPTY() {
     OPCODE = OPCODES.EMPTY;
  }
  public String toString() {
     return "EMPTY";
  }
  public String toXML() {
     return "<rvm:EMPTY/>";
  }
}
