package rePLcompiler;

import java.util.*;

public class Idp extends Hashtable<String,Integer> {
	private int idpc = 0;
	private Hashtable<Integer,String> reverse = new Hashtable<Integer,String>();

	public String reverseGet(int i) {
	    return reverse.get(i);
	}

	public Integer put(String key, Integer value) {
	    reverse.put(value,key);
	    return super.put(key,value);
	}

	public int idp(String s) {
	    if (containsKey(s))
		return ((Integer) get(s)).intValue();
	    else {
		put(s,new Integer(idpc));
		return idpc++;
	    }
	}
    }

