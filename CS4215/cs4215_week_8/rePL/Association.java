package rePL;

import java.util.*;

public class Association {

    public String property;

    public Expression expression;

    public Association(String p, Expression e) {
	property = p;
	expression = e;
    }

    public String toString() {
	return property + " : " + expression;
    }
}

