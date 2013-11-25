package cPL;

public class Signal implements Expression {

   public String varname;

   public Signal(String i) {
      varname = i;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       return "(signal " + varname + ")";
   }
}

