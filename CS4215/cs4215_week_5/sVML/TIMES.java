package sVML;

public class TIMES extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public TIMES() {
		OPCODE = OPCODES.TIMES;
	}
	public String toString() {
		return "TIMES";
	}
	public String toXML() {
		return "<svm:TIMES/>";
	}
}
