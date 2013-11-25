package cPL;

public class PropertyConstant implements Expression {
    public String value;
    public PropertyConstant(String s) {
	value = s;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return value;
    }
}

