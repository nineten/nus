package imPL;

public class While implements Expression {
	public Expression test, body;
	public While(Expression t, Expression b) {
		test = t;
		body = b;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	public StoreAndValue eval(Store s, Environment e) {
		StoreAndValue s_and_v_1 = test.eval(s, e);
		BoolValue t = (BoolValue) s_and_v_1.value;
		if (t.value) {
			StoreAndValue s_and_v_2 = body.eval(s_and_v_1.store, e);
			return this.eval(s_and_v_2.store,e);
		} else {
			return new StoreAndValue(
					s_and_v_1.store,
					new BoolValue(true));
		}
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return "(while" + test + " do " + body + ")";
	}
}
