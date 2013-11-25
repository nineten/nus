package oPL;

import java.util.*;

public class LookupApplication extends Application {

	public Expression operator;

	public Vector<Expression> operands;
	
	public Value cacheClass;
	public Value cacheRes;
	
	public LookupApplication(Expression rator, Vector<Expression> rands) {
		super(rator, rands);
	}

	// //////////////////////
	// Denotational Semantics
	// //////////////////////

	public Value eval(Environment e) {
		RecordValue objRv = (RecordValue) Store.theStore.get(e
				.access(super.operands.get(0).toString()));
		Value objClass = Store.theStore.get(objRv.get("Class"));
		
		if ((cacheClass == null) && (cacheClass != objClass)) {
			cacheRes = super.eval(e);
			cacheClass = objClass;
		}
		
		return cacheRes;
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
