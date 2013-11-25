package simPL;

// helper class for identifying whether a given 
// expression is a value (int, bool, fun, recfun)

public class IsValue {

    public static boolean isValue(Expression exp) {
	return exp instanceof BoolConstant || 
	    exp instanceof IntConstant ||
	    exp instanceof Fun ||
	    exp instanceof RecFun;
    }

}
