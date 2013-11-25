package cVML;

public class STARTTHREAD extends ADDRESS_INSTRUCTION {
  public STARTTHREAD() {
     OPCODE = OPCODES.STARTTHREAD;
  }
  public String toString() {
     return "STARTTHREAD "+ADDRESS;
  }
  public String toXML() {
     return "<rvm:STARTTHREAD/>";
  }
}
