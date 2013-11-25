package eVML;

public class LDCB extends INSTRUCTION {
   public String VALUE;
   public LDCB(String b) {
      OPCODE = OPCODES.LDCB;
      VALUE = b;
   }
  public String toString() {
     return "LDCB" + " " + VALUE;
  }
  public String toXML() {
     return "<evml:LDCB>" + VALUE + "</evml:LDCB>";
  }
}
