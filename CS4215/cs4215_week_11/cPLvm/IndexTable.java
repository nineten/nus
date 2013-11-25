package cPLvm;

import java.util.*;

public class IndexTable extends Vector<String> {

	private static final long serialVersionUID = 1L;
	public void extend(String v) {
		addElement(v);
	}
	public int access(String v) {
		return lastIndexOf(v);
	}
}

