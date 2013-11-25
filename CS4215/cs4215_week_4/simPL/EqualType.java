package simPL;

public class EqualType {

    // a simple trick to see if two types are equal:
    // transform them into strings and compare the strings!
	// (a bit non-standard, but effective and possibly
	// faster than recursing down the two data structures)

    public static boolean equalType(Type t1, Type t2) {
	return t1.toString().equals(t2.toString());
    }

}
