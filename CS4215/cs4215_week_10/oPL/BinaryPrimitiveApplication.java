package oPL;

public class BinaryPrimitiveApplication implements Expression {
	public String operator;
	public Expression argument1,argument2;

	public BinaryPrimitiveApplication(String op, Expression a1, Expression a2) {
		operator = op;
		argument1 = a1;
		argument2 = a2;
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////


	// First evaluate the first argument, then the second, and
	// then evaluate using evalPrimAppl.
	
	public Value eval(Environment e) {
		Value x1 = argument1.eval(e);
		Value x2 = argument2.eval(e);

		return evalPrimAppl(operator,x1,x2);
	}

	private static Value evalPrimAppl(String op,Value x1,Value x2) 
	{
		return
		(op.equals("+"))
		?
				new IntValue(((IntValue)x1).value + ((IntValue)x2).value)
		:
			(op.equals("-"))
			?
					new IntValue(((IntValue)x1).value - ((IntValue)x2).value)
				:
					(op.equals("*"))
					?
							new IntValue(((IntValue)x1).value * ((IntValue)x2).value)
					:
						(op.equals("/"))
						?

								new IntValue(((IntValue)x1).value / ((IntValue)x2).value)
							:
								(op.equals("<"))
								?
										((((IntValue)x1).value < ((IntValue)x2).value)
												? new BoolValue(true)
										:  new BoolValue(false)
										)
										:
											(op.equals(">"))
											?
													((((IntValue)x1).value > ((IntValue)x2).value)
															? new BoolValue(true)
													:  new BoolValue(false)
													)
													:
														(op.equals("="))
														?
																((((IntValue)x1).value == ((IntValue)x2).value)
																		? new BoolValue(true)
																:  new BoolValue(false)
																)
																:
																	(op.equals("|"))		
																	?  new BoolValue(((BoolValue)x1).value || ((BoolValue)x2).value)
																		:
																			(op.equals("&"))
																			?
																					(Value) new BoolValue(((BoolValue)x1).value && ((BoolValue)x2).value)
																	: 
																		( op.equals("."))
																		?
																				Store.theStore.get(((RecordValue) x1).get(((PropertyValue) x2).value))
																				: // op.equals("hasproperty"))
																					new BoolValue(((RecordValue) x1).containsKey(((PropertyValue) x2).value))
																				;
	}

	// //////////////////////
	// Support Functions
	// //////////////////////

	public String toString() {
		return "(" + argument1 + " " + operator + " " + argument2 + ")";
	}

}

