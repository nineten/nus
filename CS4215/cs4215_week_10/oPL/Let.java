package oPL;

import java.util.*;

public class Let implements Expression {

    public Vector<LetDefinition> definitions;

    public Expression body;

    public Let(Vector<LetDefinition> ds, Expression b) {
	definitions = ds;
	body = b;
    }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    public Value eval(Environment e) {
		LetDefinition ld;
		Value v;
		Vector<String> vars = new Vector<String>();
		Vector<Integer> locations = new Vector<Integer>();

		for (int i = 0; i < definitions.size(); i++) {
			ld = (LetDefinition) definitions.get(i);
			v = ld.rightHandExpression.eval(e);
			Integer location = Store.theStore.newLocation();
			locations.add(location);
			Store.theStore.extend(location,v);
			vars.add(ld.variable);
		}
		
		return body.eval(e.extend(vars, locations));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
       String s = "";
       for (LetDefinition d : definitions)
	   s = s + " " + d;
       return "let " + s + " in " + body + " end";
   }
}
