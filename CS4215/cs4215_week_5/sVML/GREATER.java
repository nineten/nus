package sVML;

public class GREATER extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public GREATER() {
		OPCODE = OPCODES.GREATER;
	}
	public String toString() {
		return "GREATER";
	}
	public String toXML() {
		return "<svm:GREATER/>";
	}
}
