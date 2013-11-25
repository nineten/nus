package sVML;

public class PLUS extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public PLUS() {
		OPCODE = OPCODES.PLUS;
	}
	public String toString() {
		return "PLUS";
	}
	public String toXML() {
		return "<svm:PLUS/>";
	}
}
