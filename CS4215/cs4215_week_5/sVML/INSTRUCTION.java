package sVML;

import java.io.*;

public abstract class INSTRUCTION implements Serializable {

	private static final long serialVersionUID = 1L;
	public int OPCODE;
    public abstract String toXML();
}
