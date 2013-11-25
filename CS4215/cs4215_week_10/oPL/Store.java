package oPL;

import java.util.*;

public class Store extends Vector<Value> {

	private static final long serialVersionUID = 7774258207800690736L;
	
	public static Store theStore = new Store();

   public int newLocation() {
       return size();
   }

   public void extend(int location, Value value) {
       add(location,value);
   }
}

