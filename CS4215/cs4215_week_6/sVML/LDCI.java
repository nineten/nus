package sVML;

public class LDCI extends INSTRUCTION {
   public int VALUE;
   public LDCI(int i) {
      OPCODE = OPCODES.LDCI;
      VALUE = i;
   }
  public String toString() {
      return "LDCI" + " " + VALUE;
  }
  public String toXML() {
     return "<svm:LDCI>" + VALUE + "</svm:LDCI>";
  }
}
