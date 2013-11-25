package cVML;

public class TAILCALL extends INSTRUCTION {
  public int NUMBEROFARGUMENTS;
  public TAILCALL(int noa) {
     OPCODE = OPCODES.TAILCALL;
     NUMBEROFARGUMENTS = noa;
  }
  public String toString() {
     return "TAILCALL" + " " + NUMBEROFARGUMENTS;
  }
  public String toXML() {
     return "<rvm:TAILCALL>" + NUMBEROFARGUMENTS + "</rvm:TAILCALL>";
  }
}
