package sVML;

public class EQUAL extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public EQUAL() {
		OPCODE = OPCODES.EQUAL;
	}
	public String toString() {
		return "EQUAL";
	}
	public String toXML() {
		return "<svm:EQUAL/>";
	}
}
