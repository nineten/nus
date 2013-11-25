package rePLvm;

import rePLcompiler.*;

public class RecordValue implements Value{
    int idr;
    Value[] entries = null;
    int[] i_to_idp = null;
    Idp idp;
		
    public RecordValue(int thisIdr, int size,Idp thisIdp) {
	idr = thisIdr;
	if (size > 0) {
	    entries = new Value[size];
	}
	idp = thisIdp;
    }
		
    public String toString() {
	String retval = "[";
	if (entries == null)
	    retval += "]";
	else {
	    for (int i = 0; i < entries.length - 1; i++)
		retval += idp.reverseGet(i_to_idp[i])+ ":" + entries[i] + ", ";
	    retval += idp.reverseGet(i_to_idp[entries.length - 1])+":"
		+entries[entries.length - 1];
	    retval += "]";
	}
	return retval;
    }
}
	
