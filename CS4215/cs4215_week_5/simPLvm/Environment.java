package simPLvm;

import java.util.*;

public class Environment extends Vector<Value> {

	private static final long serialVersionUID = 1L;

	public Environment extend(int numberOfSlots) {
		Environment newEnvironment = (Environment) clone();
		newEnvironment.setSize(newEnvironment.size() + numberOfSlots);
		return newEnvironment;
	}
}

