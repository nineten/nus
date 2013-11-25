package sVML;

public class LDF extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public int ADDRESS;
	public int NUMBEROFARGUMENTS;
	public LDF() {
		OPCODE = OPCODES.LDF;
		NUMBEROFARGUMENTS = 0;
		ADDRESS = 0;
	}
	public LDF(int noa) {
		OPCODE = OPCODES.LDF;
		NUMBEROFARGUMENTS = noa;
		ADDRESS = 0;
	}
	public String toString() {
		return "LDF" + " " + ADDRESS;
	}
	public String toXML() {
		return "<svm:LDF>" + ADDRESS + "</svm:LDF>";
	}
}
