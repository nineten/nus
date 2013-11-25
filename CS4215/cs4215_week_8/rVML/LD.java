package rVML;

public class LD extends INSTRUCTION {
   public int INDEX;
   public LD(int i) {
      OPCODE = OPCODES.LD;
      INDEX = i;
   }
  public String toString() {
     return "LD" + " " + INDEX;
  }
  public String toXML() {
     return "<rvm:LD>"+ INDEX + "</rvm:LD>";
  }
}
