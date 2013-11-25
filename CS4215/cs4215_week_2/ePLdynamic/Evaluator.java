package ePLdynamic;

import ePL.*;

class Evaluator {

	// evaluate repeatedly implements the evaluation relation
	// by repeatedly calling oneStep until the expression is
	// not reducible
	static public Expression evaluate(Expression exp)  {
		return reducible(exp) ? 
				evaluate(oneStep(exp))
				: exp;
	}

	// in ePL, a reducible expression is a PrimitiveApplication that
	// has a reducible expression as one of its arguments, or that has constants
	// of the right type as arguments

	static private boolean reducible(Expression exp) {
		if (exp instanceof BinaryPrimitiveApplication)
		{
			return
					reducible(((BinaryPrimitiveApplication)exp).argument1)
					||
					reducible(((BinaryPrimitiveApplication)exp).argument2)
					||
					// integer ops without division
					(
							((BinaryPrimitiveApplication)exp).operator.equals("+") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals("*") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals("-") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals("=") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals("<") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals(">") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals("&") 
							||
							((BinaryPrimitiveApplication)exp).operator.equals("|") 
							)
							&&
							((((BinaryPrimitiveApplication)exp).argument1 instanceof IntConstant
							&&
							((BinaryPrimitiveApplication)exp).argument2 instanceof IntConstant
							)
							||
							(((BinaryPrimitiveApplication)exp).argument1 instanceof BoolConstant
							&&
							((BinaryPrimitiveApplication)exp).argument2 instanceof BoolConstant
							))
							||
							// division (exclude second argument 0)
							((BinaryPrimitiveApplication)exp).operator.equals("/") 
							&&
							((BinaryPrimitiveApplication)exp).argument1 instanceof IntConstant
							&&
							((BinaryPrimitiveApplication)exp).argument2 instanceof IntConstant
							&&
							! ((IntConstant)(((BinaryPrimitiveApplication)exp).argument2))
							.value.equals("0")
							;
		}
		else
			return false;
	}

	// oneStep finds the place where a given expression is
	// reducible, and carries out the reduction at that place
	
	static private Expression oneStep(Expression exp) {
		if (exp instanceof UnaryPrimitiveApplication) {
			Expression arg = ((UnaryPrimitiveApplication)exp).argument;
			/* ((PrimitiveApplication)exp.operator.equals("\\")) */ 
			return new 
					BoolConstant(Boolean.toString(! ((BoolConstant)arg)
							.value.equals("true")));

		} else {
			String operator = ((BinaryPrimitiveApplication)exp).operator;
			Expression firstArg = ((BinaryPrimitiveApplication)exp).argument1;
			Expression secondArg = ((BinaryPrimitiveApplication)exp).argument2;
			if (reducible(firstArg)) {
				return new
						BinaryPrimitiveApplication(operator,oneStep(firstArg),secondArg);
			} else if (reducible(secondArg)) {
				return new 
						BinaryPrimitiveApplication(operator,firstArg,oneStep(secondArg));
			} else if (operator.equals("+")) {
				return new 
						IntConstant(Integer.toString(Integer
								.parseInt(((IntConstant)firstArg)
										.value)
										+
										Integer
										.parseInt(((IntConstant)secondArg)
												.value)));
			} else if (operator.equals("-")) {
				return new 
						IntConstant(Integer.toString(Integer
								.parseInt(((IntConstant)firstArg)
										.value)
										-
										Integer
										.parseInt(((IntConstant)secondArg)
												.value)));
			} else if (operator.equals("*")) {
				return new 
						IntConstant(Integer.toString(Integer
								.parseInt(((IntConstant)firstArg)
										.value)
										*
										Integer
										.parseInt(((IntConstant)secondArg)
												.value)));
			} else if (operator.equals("/")) {
				return new 
						IntConstant(Integer.toString(Integer
								.parseInt(((IntConstant)firstArg)
										.value)
										/
										Integer
										.parseInt(((IntConstant)secondArg)
												.value)));
				// of course the following is not correct.
				// you need to handle all cases, so that
				// there is no need for a final "else" part
			} else if (operator.equals("=")) {
				return new BoolConstant(Boolean.toString(Integer
						.parseInt(((IntConstant)firstArg)
								.value)
								==
								Integer
								.parseInt(((IntConstant)secondArg)
										.value)));
			} else if (operator.equals("<")) {
				return new BoolConstant(Boolean.toString(Integer
						.parseInt(((IntConstant)firstArg)
								.value)
								<
								Integer
								.parseInt(((IntConstant)secondArg)
										.value)));
			} else if (operator.equals(">")) {
				return new BoolConstant(Boolean.toString(Integer
						.parseInt(((IntConstant)firstArg)
								.value)
								>
								Integer
								.parseInt(((IntConstant)secondArg)
										.value)));
			} else if (operator.equals("&")) {
				return new BoolConstant(Boolean.toString(Boolean
						.parseBoolean(((BoolConstant)firstArg)
								.value)
								&&
								Boolean
								.parseBoolean(((BoolConstant)secondArg)
										.value)));
			} else if (operator.equals("|")) {
				return new BoolConstant(Boolean.toString(Boolean
						.parseBoolean(((BoolConstant)firstArg)
								.value)
								||
								Boolean
								.parseBoolean(((BoolConstant)secondArg)
										.value)));
			} else {
				return new
						IntConstant("0");
			}
		}
	}
}

