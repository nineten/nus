package simPL;

import java.util.*;

// a non-destructive implementation of sets
// all operations leave this and the argument
// unchanged

class StringSet extends HashSet<String> {

	private static final long serialVersionUID = 1L;

	// construct empty set
    StringSet() {
    }

    // construct singleton set
    StringSet(String x) {
	add(x);
    }

    // return this - {x}, without changing this
    StringSet subtract(String x) {
	StringSet result = (StringSet) this.clone();
	result.remove(x);
	return result;
    }

    // returns this n x, without changing this
    StringSet intersect(Collection<String> x) {
	StringSet result = (StringSet) this.clone();
	for (String s : this) 
	    if (! x.contains(s))
		result.remove(s);
	return result;
    }

    // return this - x, without changing this
    StringSet difference(Collection<String> x) {
	StringSet result = (StringSet) this.clone();
	result.removeAll(x);
	return result;
    }

    // return this U x, without changing this
    StringSet union(Collection<String> x) {
	StringSet result = (StringSet) this.clone();
	result.addAll(x);
	return result;
    }
}
