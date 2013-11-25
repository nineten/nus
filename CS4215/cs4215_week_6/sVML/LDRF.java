package sVML;

public class LDRF extends LDF {
   public LDRF() {
      OPCODE = OPCODES.LDRF;
      ADDRESS = 0;
   }
  public String toString() {
     return "LDRF" + " " + ADDRESS + " " + MAXSTACKSIZE;
  }
  public String toXML() {
     return "<svm:LDRF><svm:ADDRESS>" + ADDRESS + "</svm:ADDRESS><svm:MAXSTACKSIZE>"+MAXSTACKSIZE+"</svm:MAXSTACKSIZE></svm:LDRF>";
  }
}
