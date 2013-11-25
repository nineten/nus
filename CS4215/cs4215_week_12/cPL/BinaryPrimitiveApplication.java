package cPL;

public class BinaryPrimitiveApplication implements Expression {
    public String operator;
    public Expression argument1,argument2;

    public BinaryPrimitiveApplication(String op, Expression a1, Expression a2) {
	operator = op;
	argument1 = a1;
	argument2 = a2;
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	return "(" + argument1 + " " + operator + " " + argument2 + ")";
    }
}

