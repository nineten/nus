package cVML;

public class POP extends INSTRUCTION {
  public POP() {
     OPCODE = OPCODES.POP;
  }
  public String toString() {
     return "POP";
  }
  public String toXML() {
     return "<rvm:POP/>";
  }
}
