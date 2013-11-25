package cVML;

public class LDCB extends INSTRUCTION {
   public boolean VALUE;
   public LDCB(boolean b) {
      OPCODE = OPCODES.LDCB;
      VALUE = b;
   }
  public String toString() {
     return "LDCB" + " " + VALUE;
  }
  public String toXML() {
     return "<rvm:LDCB>" + VALUE + "</rvm:LDCB>";
  }
}
