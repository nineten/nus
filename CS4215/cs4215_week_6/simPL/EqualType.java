package simPL;

public class EqualType {

    // a simple trick to see if two types are equal:
    // transform them into strings and compare the strings!

    public static boolean equalType(Type t1, Type t2) {
	return t1.toString().equals(t2.toString());
    }

}
