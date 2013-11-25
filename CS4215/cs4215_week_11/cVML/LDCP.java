package cVML;

public class LDCP extends INSTRUCTION {
   public String VALUE;
   public LDCP(String s) {
      OPCODE = OPCODES.LDCP;
      VALUE = s;
   }
  public String toString() {
     return "LDCP" + " " + VALUE;
  }
  public String toXML() {
     return "<rvm:LDCP>" + VALUE + "</rvm:LDCP>";
  }
}
