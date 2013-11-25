package oPL;

import java.util.*;

public class RecordValue extends Hashtable<String,Integer> implements Value{

	private static final long serialVersionUID = 1L;

	public String toString() {
	if (size()==0) return "[]";
	else {
	    String s="[";
	    Enumeration<String> ks = keys();
	    while (ks.hasMoreElements()) {
		String k = ks.nextElement();
		s = s + k + " : "+Store.theStore.get(get(k))+", ";
	    }
	    return s.substring(0,s.length()-2) + "]";
	}
    }
}