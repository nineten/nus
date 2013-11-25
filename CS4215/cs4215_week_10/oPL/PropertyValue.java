package oPL;

public class PropertyValue implements Value {
  public String value;
  public PropertyValue(String v) {
    value = v;
  }
    public String toString() {
	return value;
    }
}

