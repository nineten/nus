package rVML;

public class RCD extends INSTRUCTION {
  public int NUMBEROFARGUMENTS;
  public int IDR;
  public RCD(int noa, int idr) {
     OPCODE = OPCODES.RCD;
     NUMBEROFARGUMENTS = noa;
     IDR = idr;
  }
  public String toString() {
      return "RCD" + " " + NUMBEROFARGUMENTS + " " + IDR;
  }
  public String toXML() {
     return "<rvm:RCD>" + NUMBEROFARGUMENTS + " " + IDR + "</rvm:RCD>";
  }
}
