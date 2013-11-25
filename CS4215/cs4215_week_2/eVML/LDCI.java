package eVML;

public class LDCI extends INSTRUCTION {
   public String VALUE;
   public LDCI(String i) {
      OPCODE = OPCODES.LDCI;
      VALUE = i;
   }
  public String toString() {
      return "LDCI" + " " + VALUE;
  }
  public String toXML() {
     return "<evml:LDCI>" + VALUE + "</evml:LDCI>";
  }
}
