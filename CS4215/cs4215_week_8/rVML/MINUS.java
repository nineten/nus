package rVML;

public class MINUS extends INSTRUCTION {
  public MINUS() {
     OPCODE = OPCODES.MINUS;
  }
  public String toString() {
     return "MINUS";
  }
  public String toXML() {
     return "<rvm:MINUS/>";
  }
}
