package sVML;

public class DONE extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public DONE() {
		OPCODE = OPCODES.DONE;
	}
	public String toString() {
		return "DONE";
	}
	public String toXML() {
		return "<svm:DONE/>";
	}
}
