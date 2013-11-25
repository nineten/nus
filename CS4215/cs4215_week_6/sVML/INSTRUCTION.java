package sVML;

import java.io.*;

public abstract class INSTRUCTION implements Serializable {
    public int OPCODE;
    public abstract String toXML();
}
