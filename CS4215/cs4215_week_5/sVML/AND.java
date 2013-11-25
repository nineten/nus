package sVML;

public class AND extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public AND() {
		OPCODE = OPCODES.AND;
	}
	public String toString() {
		return "AND";
	}
	public String toXML() {
		return "<svm:AND/>";
	}
}
