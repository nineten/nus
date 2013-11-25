package simPL;

import java.util.*;

public class FunType implements Type {
    public Vector<Type> argumentTypes;
    public Type returnType;

    public FunType(Vector<Type> as, Type r) {
	argumentTypes = as;
	returnType = r;
    }

    public Vector<Type> toVector() {
	Vector<Type> v = new Vector<Type>();
	v.insertElementAt(this,0);
	return v;
    }

    public String toString() {
	String s = "";
	for (Type t : argumentTypes)
	    s = s + " * " + t;
	return "(" + s.substring(3) + " -> " + returnType + ")";
    }
}

