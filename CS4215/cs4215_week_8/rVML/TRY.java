package rVML;

public class TRY extends LDF {
   public TRY() {
      OPCODE = OPCODES.TRY;
      ADDRESS = 0;
   }
  public String toString() {
     return "TRY" + " " + ADDRESS;
  }
  public String toXML() {
     return "<rvm:TRY>" + ADDRESS + "</rvm:TRY>";
  }
}
