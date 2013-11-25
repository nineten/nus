package oPL;

public class PropertyConstant implements Expression {
  public String value;
  public PropertyConstant(String v) {
    value = v;
  }
  
  public Value eval(Environment e) {
	  return new PropertyValue(value);
  }
  
    public String toString() {
	return value;
    }
}

