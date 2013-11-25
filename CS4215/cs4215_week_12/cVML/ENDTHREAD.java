package cVML;

public class ENDTHREAD extends INSTRUCTION {

   public ENDTHREAD() {
     OPCODE = OPCODES.ENDTHREAD;
   }

   public String toString() {
       return "ENDTHREAD";
   }
  public String toXML() {
      return "<cvm:ENDTHREAD/>";
  }
}
