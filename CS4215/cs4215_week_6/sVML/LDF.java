package sVML;

public class LDF extends INSTRUCTION {
    public int ADDRESS;
    public int MAXSTACKSIZE;
   public LDF() {
      OPCODE = OPCODES.LDF;
      ADDRESS = 0;
   }
   public String toString() {
      return "LDF" + " " + ADDRESS + " " + MAXSTACKSIZE;
   }
  public String toXML() {
     return "<svm:LDF><svm:ADDRESS>" + ADDRESS + "</svm:ADDRESS><svm:MAXSTACKSIZE>"+MAXSTACKSIZE+"</svm:MAXSTACKSIZE></svm:LDF>";
  }
}
