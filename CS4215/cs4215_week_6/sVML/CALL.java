package sVML;

public class CALL extends INSTRUCTION {
  public int NUMBEROFARGUMENTS;
  public CALL(int noa) {
     OPCODE = OPCODES.CALL;
     NUMBEROFARGUMENTS = noa;
  }
  public String toString() {
     return "CALL" + " " + NUMBEROFARGUMENTS;
  }
  public String toXML() {
     return "<svm:CALL>" + NUMBEROFARGUMENTS + "</svm:CALL>";
  }
}
