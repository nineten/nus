package sVML;

public class OR extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public OR() {
		OPCODE = OPCODES.OR;
	}
	public String toString() {
		return "OR";
	}
	public String toXML() {
		return "<svm:OR/>";
	}
}
