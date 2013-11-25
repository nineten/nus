package cVML;

public class ENDTRY extends INSTRUCTION {

   public ENDTRY() {
     OPCODE = OPCODES.ENDTRY;
   }

   public String toString() {
       return "ENDTRY";
   }
  public String toXML() {
      return "<cvm:ENDTRY/>";
  }
}
