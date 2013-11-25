package cVML;

public class START extends ADDRESS_INSTRUCTION {
  public START() {
     OPCODE = OPCODES.START;
  }
  public String toString() {
     return "START";
  }
  public String toXML() {
     return "<rvm:START/>";
  }
}
