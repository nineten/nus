package ePL;

public class IntConstant implements Expression {
   public String value;
   public IntConstant(String v) {
      value = v;
   }
   public String toString() {
       return value;
   }
   public String toXML() {
       return "<epl:intconstant>" + value + "</epl:intconstant>\n";
   }
}

