package sVML;

public class LESS extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public LESS() {
		OPCODE = OPCODES.LESS;
	}
	public String toString() {
		return "LESS";
	}
	public String toXML() {
		return "<svm:LESS/>";
	}
}
