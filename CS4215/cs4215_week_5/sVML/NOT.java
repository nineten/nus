package sVML;

public class NOT extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public NOT() {
		OPCODE = OPCODES.NOT;
	}
	public String toString() {
		return "NOT";
	}
	public String toXML() {
		return "<svm:NOT/>";
	}
}
