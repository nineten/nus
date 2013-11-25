package oPL;

public class Variable implements Expression {

   public String varname;

   public Variable(String nam) {
      varname = nam;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    // stub to be replaced by proper implementation

    public Value eval(Environment e) {
    	return Store.theStore.get(e.access(varname));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return varname;
    }
}

