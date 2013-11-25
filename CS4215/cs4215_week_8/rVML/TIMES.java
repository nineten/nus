package rVML;

public class TIMES extends INSTRUCTION {
  public TIMES() {
     OPCODE = OPCODES.TIMES;
  }
  public String toString() {
     return "TIMES";
  }
  public String toXML() {
     return "<rvm:TIMES/>";
  }
}
