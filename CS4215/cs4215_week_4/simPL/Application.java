package simPL;

import java.util.*;

public class Application implements Expression {

    public Expression operator;

    public Vector<Expression> operands;

    public Application(Expression rator,Vector<Expression> rands) {
	operator = rator; operands = rands;
    }

    public Expression eliminateLet() {
    	Vector<Expression> newoperands = new Vector<Expression>();
    	for (Expression operand : operands) 
    	    newoperands.add(operand.eliminateLet());
    	return new Application(operator.eliminateLet(), newoperands);
    }
    
    // to be implemented by student

	public Type check(TypeEnvironment G) throws TypeError {
		int i=0;
		if (operator instanceof Fun)
		{
			Type funType = operator.check(G);
			Vector<Type> argType = ((FunType)funType).argumentTypes;
			if ( (argType.size()) != (operands.size()) )
				throw new TypeError("number of operands do not match: "+this);
			for (Type argT : argType)
			{
				if (operands.get(i).check(G).getClass() != argT.getClass())
					throw new TypeError("operand type mismatched: "+this);
				i++;
			}
			return ((FunType)funType).returnType;
		}
		
		//handles RecFun Types
		if (G.access(operator.toString()) instanceof FunType)
		{
			return ((FunType)G.access(operator.toString())).returnType;
		}
		return null;
	}

    // //////////////////////
    // Support Functions
    // //////////////////////

    public String toString() {
	String s = "";
	for  (Expression operand : operands) 
	    s = s + " " + operand;
	return "("+operator+" "+s+")";
    }
}
