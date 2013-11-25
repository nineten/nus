package cVML;

public abstract class ADDRESS_INSTRUCTION extends INSTRUCTION {
    public int ADDRESS = 0;
    public String toXML() {
	return "<cvml:ADDRESS_INSTRUCTION/>";
    }
}
