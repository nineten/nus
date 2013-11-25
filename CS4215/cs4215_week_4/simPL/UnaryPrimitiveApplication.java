package simPL;

public class UnaryPrimitiveApplication implements Expression {

	public String operator;

	public Expression argument;

	public UnaryPrimitiveApplication(String op, Expression a) {
		operator = op;
		argument = a;
	}

	// Eliminate the lets in the operand
	public Expression eliminateLet() {
		return new UnaryPrimitiveApplication(operator,
				argument.eliminateLet());
	}


	public Type check(TypeEnvironment G) throws TypeError {
		Type result = argument.check(G);
		if(result instanceof BoolType && operator.equals("\\")) 
			return new BoolType();
		else throw new TypeError("ill-typed unary primitive application "+this);
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return operator + "(" + argument + ")";
	}
}

