package sVML;

public class START extends INSTRUCTION {
    public int MAXSTACKSIZE;
   public START() {
      OPCODE = OPCODES.START;       
   }
   public String toString() {
      return "START" + " " + MAXSTACKSIZE;
   }
  public String toXML() {
     return "<svm:START>" + MAXSTACKSIZE + "</svm:START>";
  }
}
