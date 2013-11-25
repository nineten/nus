package cPL;

public class Wait implements Expression {

   public String varname;

   public Wait(String i) {
      varname = i;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(wait " + varname + ")";
   }
}

