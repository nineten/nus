package simPL;

import java.util.*;

public class Fun implements Expression {

	public Type funType;
	public Vector<String> formals;
	public Expression body;

	public Fun(Type t, Vector<String> xs, Expression b) {
		funType = t;
		formals = xs;
		body = b;
	}

	public Expression eliminateLet() {
		return new Fun(funType, formals, body.eliminateLet());
	}

	// //////////////////////
	// Static Semantics
	// //////////////////////

	public Type check(TypeEnvironment G) throws TypeError {
		if (! (funType instanceof FunType)) throw new TypeError("declared type of function "+this+
				" must be a function type, but instead is "+funType);
		FunType functionType = (FunType)funType;

		// check that formals are pairwise distinct
		Vector<String> distinctFormals = new Vector<String>();
		for (String f : formals) {
			if (distinctFormals.contains(f)) {
				throw new TypeError("variable "+f+" appears multiple times in parameter list");
			}
			distinctFormals.add(f);
		}

		// check if formal list and type list have equal size
		int formalSize = formals.size();
		int typeSize = functionType.argumentTypes.size();

		if (formalSize != typeSize) throw new TypeError("number of argument types does not coincide with "+
				"number of parameters in "+this
		);

		// create environment gamma(n+1)
		TypeEnvironment newG = G.extend(formals,functionType.argumentTypes);

		// check type of E: get return type of body
		Type eType = body.check(newG);

		// if return type of Fun == return type of body expression;
		if (EqualType.equalType(functionType.returnType,eType))
			return funType;
		else throw new TypeError("declared return type does not match type of body in "+this);
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		String s = "";
		for (String f : formals)
			s = s + " " + f;
		return "fun {" + funType + "}" + 
		s + " -> " + body + " end";
	}

	public String toXML() {
		String s = "";
		for (String f : formals) 
			s = s + "<simpl:formal>" + f + "</simpl:formal>";
		return "<simpl:fun>\n" 
		+ "<simpl:funtype>" + funType.toXML() + "</simpl:funtype>\n"
		+ "<simpl:formals>" + s + "</simpl:formals>\n"
		+ "<simpl:body>\n" + body.toXML() + "</simpl:body>\n"
		+ "</simpl:fun>\n";
	}
}
