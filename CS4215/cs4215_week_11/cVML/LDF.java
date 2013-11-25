package cVML;

public class LDF extends ADDRESS_INSTRUCTION{
   public int NUMBEROFARGUMENTS;
   public LDF() {
      OPCODE = OPCODES.LDF;
      NUMBEROFARGUMENTS = 0;
      ADDRESS = 0;
   }
   public LDF(int noa) {
      OPCODE = OPCODES.LDF;
      NUMBEROFARGUMENTS = noa;
      ADDRESS = 0;
   }
   public String toString() {
      return "LDF" + " " + ADDRESS;
   }
  public String toXML() {
     return "<rvm:LDF>" + ADDRESS + "</rvm:LDF>";
  }
}
