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

    // //////////////////////
    // Static Semantics
    // //////////////////////

    public Type check(TypeEnvironment G) throws TypeError {

	Type operatorType = operator.check(G);

	if (operatorType instanceof FunType) {
			
	    FunType operatorFunType = (FunType) operatorType;
	    int sizeOfOperands = operands.size();
	    int sizeOfFormals = operatorFunType.argumentTypes.size();

	    if (sizeOfOperands == sizeOfFormals) {
		Enumeration<Type> formalTypeList = operatorFunType.argumentTypes.elements();
		Enumeration<Expression> operandList = operands.elements();
		int position = 0;
		while(operandList.hasMoreElements()){
		    position++;
		    Type operandType = ((Expression) operandList.nextElement()).check(G);
		    Type formalType = (Type) formalTypeList.nextElement();
		    if(!EqualType.equalType(operandType,formalType))
			throw new TypeError("argument "+position+" has type "+operandType+" in "+this+" but function "+
					    "expects arguments of type "+formalType);
		}
		return operatorFunType.returnType;
	    } else throw new TypeError("operator function in application "+this+" expects "+sizeOfFormals+" argument"+
	    		( (sizeOfFormals==1) ? "" : "s" )+
				       ", but gets applied to "+sizeOfOperands+" argument"+
				       ( (sizeOfOperands==1) ? "" : "s"));
	} else throw new TypeError("operator in application "+this+" does not have function type but "+operatorType);
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

    public String toXML() {
	String s = "";
	for (Expression operand : operands)
	    s = s + operand.toXML();
	return "<simpl:application>\n"
	    +"<simpl:operator>\n"
	    +operator.toXML()
	    +"</simpl:operator>\n"
	    +"<simpl:arguments>\n"
	    +s
	    +"</simpl:arguments>\n"
	    +"</simpl:application>\n";
    }
}
