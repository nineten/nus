package sVML;

public class DIV extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public DIV() {
		OPCODE = OPCODES.DIV;
	}
	public String toString() {
		return "DIV";
	}
	public String toXML() {
		return "<svm:DIV/>";
	}
}
