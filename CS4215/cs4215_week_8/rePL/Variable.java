package rePL;

public class Variable implements Expression {

   public String varname;

   public Variable(String nam) {
      varname = nam;
   }

    public Expression eliminateLet() {
	return this;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return varname;
    }
}

