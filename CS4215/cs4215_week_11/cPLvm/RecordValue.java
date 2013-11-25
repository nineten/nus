package cPLvm;

import java.util.*;

public class RecordValue extends Hashtable<String,Value> implements Value{

	private static final long serialVersionUID = 1L;

	public String toString() {
	String s="[";
	Enumeration<String> ks = keys();
	while (ks.hasMoreElements()) {
	    String k = ks.nextElement();
	    s = s + k + " : "+get(k)+", ";
	}
	return s.substring(0,s.length()-2) + "]";
    }
}
	
