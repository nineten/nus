package imPL;

public class Variable implements Expression {

   public String varname;

   public Variable(String nam) {
      varname = nam;
   }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    public StoreAndValue eval(Store s, Environment e) {
    		return new StoreAndValue(s,s.get(e.access(varname)));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return varname;
    }
}

