package imPL;

import java.util.*;

public class RecFun extends Fun {

	public String funVar;

	public RecFun(String f, Vector<String> xs, Expression b) {
		super(xs, b);
		funVar = f;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	// stub to be replaced by proper implementation

	public StoreAndValue eval(Store s, Environment e) {
		StoreAndValue res;
		int newloc;
		res = super.eval(s, e);
		res.store = res.store.extend(res.store.size(), res.value);
		((FunValue) res.value).environment = ((FunValue) res.value).environment
				.extend(funVar, res.store.size() - 1);
		res = new StoreAndValue(res.store, new FunValue(
				((FunValue) res.value).environment, formals, body));
		return res;
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		String s = "";
		for (String f : formals)
			s = s + " " + f;
		return "recfun " + funVar + s + " -> " + body + " end";
	}
}
