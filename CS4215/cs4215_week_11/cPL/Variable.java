package cPL;

public class Variable implements Expression {

   public String varname;

   public Variable(String nam) {
      varname = nam;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return varname;
    }
}

