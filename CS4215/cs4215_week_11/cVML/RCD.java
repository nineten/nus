package cVML;

public class RCD extends INSTRUCTION {
    public int NUMBEROFARGUMENTS;
    public int IDR;
    public RCD(int noa) {
	OPCODE = OPCODES.RCD;
	NUMBEROFARGUMENTS = noa;
    }
    public String toString() {
	return "RCD" + " " + NUMBEROFARGUMENTS;
    }
    public String toXML() {
	return "<rvm:RCD>" + NUMBEROFARGUMENTS + "</rvm:RCD>";
    }
}
