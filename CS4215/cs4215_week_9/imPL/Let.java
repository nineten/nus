package imPL;

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

    // stub to be replaced by proper implementation

    public StoreAndValue eval(Store s, Environment e) {
    	LetDefinition letDef;
    	for (int x=0; x<definitions.size(); x++) {
    		letDef = definitions.get(x);
    		s = s.extend(s.size(), (letDef.rightHandExpression.eval(s, e)).value);
    		e = e.extend(letDef.variable, s.size()-1);
    	}
    	
    	StoreAndValue res = body.eval(s, e);
    	
	return res;
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
