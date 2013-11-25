package ePL;

public class BoolConstant implements Expression {
   public String value;
   public BoolConstant(String v) {
      value = v;
   }
   public String toString() {
       return value;
   }
    public String toXML() {
	return "<epl:boolconstant>" + value + "</epl:boolconstant>\n";
    }
}

