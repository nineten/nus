package sVML;

public class LDRF extends LDF {

	private static final long serialVersionUID = 1L;
	public LDRF() {
		OPCODE = OPCODES.LDRF;
		NUMBEROFARGUMENTS = 0;
		ADDRESS = 0;
	}
	public LDRF(int noa) {
		OPCODE = OPCODES.LDRF;
		NUMBEROFARGUMENTS = noa;
		ADDRESS = 0;
	}
	public String toString() {
		return "LDRF" + " " + ADDRESS;
	}
	public String toXML() {
		return "<svm:LDRF>" + ADDRESS + "</svm:LDRF>";
	}
}
