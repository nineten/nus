package imPL;

import java.util.*;

public class Application implements Expression {

	public Expression operator;

	public Vector<Expression> operands;

	public Application(Expression rator, Vector<Expression> rands) {
		operator = rator;
		operands = rands;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	// stub to be replaced by proper implementation

	public StoreAndValue eval(Store s, Environment e) {
		StoreAndValue res, funRes;
		Enumeration<String> listOfE;

		if ((operator instanceof Fun) || (operator instanceof RecFun)) {
			funRes = operator.eval(s, e);

			listOfE = e.keys();
			String key;
			while (listOfE.hasMoreElements()) {
				key = listOfE.nextElement();
				if (!((FunValue) funRes.value).environment.containsKey(key)) {
					((FunValue) funRes.value).environment = ((FunValue) funRes.value).environment
							.extend(key, e.access(key));
				}
			}

			for (int x = 0; x < ((FunValue) funRes.value).formals.size(); x++) {
				funRes.store.setElementAt(operands.get(x).eval(s, e).value,
						((FunValue) funRes.value).environment
								.access(((FunValue) funRes.value).formals
										.get(x)));
			}

			res = ((FunValue) funRes.value).body.eval(funRes.store,
					((FunValue) funRes.value).environment);
		} else {
			FunValue funVal = (FunValue) s.get(e.access(operator.toString()));
			Fun f = new Fun(funVal.formals, funVal.body);
			Application app = new Application(f, operands);
			res = app.eval(s, e);
		}

		return res;
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		String s = "";
		for (Expression operand : operands)
			s = s + " " + operand;
		return "(" + operator + " " + s + ")";
	}
}
