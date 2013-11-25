package ePL;

public class BinaryPrimitiveApplication implements Expression {
   public String operator;
   public Expression argument1,argument2;
   public BinaryPrimitiveApplication(String op, Expression a1, Expression a2) {
      operator = op;
      argument1 = a1;
      argument2 = a2;
   }
   public String toString() {
       return "(" + argument1 + " " + operator + " " + argument2 + ")";
   }
   public String toXML() {
       return "<epl:binaryprimitiveapplication>\n" 
	   + "<epl:operator>" 
	   + ( (operator.equals(">")) 
	       ? "&gt;"
	       : (operator.equals("<")) 
	       ? "&lt;"
	       : (operator.equals("&"))
	       ? "&amp;"
	       :
	       operator )
	   + "</epl:operator>\n"
	   + "<epl:argument1>\n" + argument1.toXML() + "</epl:argument1>\n" 
	   + "<epl:argument2>\n" + argument2.toXML() + "</epl:argument2>\n" 
	   + "</epl:binaryprimitiveapplication>\n";
   }
}
