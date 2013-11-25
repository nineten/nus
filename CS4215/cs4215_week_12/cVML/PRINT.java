package cVML;

public class PRINT extends INSTRUCTION {
  public PRINT() {
     OPCODE = OPCODES.PRINT;
  }
  public String toString() {
     return "PRINT";
  }
  public String toXML() {
     return "<rvm:PRINT/>";
  }
}
