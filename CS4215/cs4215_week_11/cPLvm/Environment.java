package cPLvm;

import java.util.*;

public class Environment extends Vector<Value> {

	private static final long serialVersionUID = 1L;
	
	// environments are arranged in hierarchies;
	private Environment previous;
	
	@Override
	// set is used in ASSIGN
	// set overwrites only non-null values;
	// if current value is null, go to previous environment
	public Value set(int i, Value v) {
		Value old = super.elementAt(i);
		if (old==null)
			return previous.set(i,v);
		else return super.set(i,v);
	}
	
	@Override
	// elementAt is used in LD
	// return only non-null elements;
	// if current value is null, go to previous environment
	public Value elementAt(int i) {
		Value v = super.elementAt(i);
		if (v==null) 
			return previous.elementAt(i);
		else return v;
	}

	// environments are extended by creating new environments
	// that have null where the previous environment had values
	public Environment extend(int numberOfSlots) {
		Environment newEnvironment = new Environment();
		newEnvironment.setSize(this.size()+numberOfSlots);
		newEnvironment.previous = this;
		return newEnvironment;
	}
}

