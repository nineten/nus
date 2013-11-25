package sVML;

public class MINUS extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public MINUS() {
		OPCODE = OPCODES.MINUS;
	}
	public String toString() {
		return "MINUS";
	}
	public String toXML() {
		return "<svm:MINUS/>";
	}
}
