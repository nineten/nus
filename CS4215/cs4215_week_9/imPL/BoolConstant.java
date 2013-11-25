package imPL;

public class BoolConstant implements Expression {
	public boolean value;
	public BoolConstant(boolean v) {
		value = v;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	public StoreAndValue eval(Store s, Environment e) {
		return new StoreAndValue(s,new BoolValue(value));
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return (new Boolean(value)).toString();
	}
}

