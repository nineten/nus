package imPL;

import java.util.*;

public class Store extends Vector<Value> {

	private static final long serialVersionUID = 4677156750248393275L;

	public int newLocation() {
		return size();
	}

	public Store extend(int location, Value value) {
		Store newStore = (Store) clone();
		newStore.add(location,value);
		return newStore;
	}
}

