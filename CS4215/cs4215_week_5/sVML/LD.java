package sVML;

public class LD extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public int INDEX;
	public LD(int i) {
		OPCODE = OPCODES.LD;
		INDEX = i;
	}
	public String toString() {
		return "LD" + " " + INDEX;
	}
	public String toXML() {
		return "<svm:LD>"+ INDEX + "</svm:LD>";
	}
}
