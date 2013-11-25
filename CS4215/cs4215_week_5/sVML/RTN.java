package sVML;

public class RTN extends INSTRUCTION {

	private static final long serialVersionUID = 1L;
	public RTN() {
		OPCODE = OPCODES.RTN;
	}
	public String toString() {
		return "RTN";
	}
	public String toXML() {
		return "<svm:RTN/>";
	}
}
