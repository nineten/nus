package imPL;

public class IntConstant implements Expression {
    public int value;

    public IntConstant(int v) {
	value = v;
    }

    // //////////////////////
    // Denotational Semantics
    // //////////////////////

    public StoreAndValue eval(Store s, Environment e) {
    		return new StoreAndValue(s,new IntValue(value));
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return Integer.toString(value);
    }
}

