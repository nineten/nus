package rVML;

public class THROW extends INSTRUCTION {
  public THROW() {
     OPCODE = OPCODES.THROW;
  }
  public String toString() {
     return "THROW";
  }
  public String toXML() {
     return "<rvm:THROW/>";
  }
}
