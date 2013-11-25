package ePL;

public class UnaryPrimitiveApplication implements Expression {
    public String operator;
    public Expression argument;
    public UnaryPrimitiveApplication(String op, Expression a) {
      operator = op;
      argument = a;
   }
   public String toString() {
       return operator + "(" + argument + ")";
   }
   public String toXML() {
       return "<epl:unaryprimitiveapplication>\n" 
	   + "<epl:operator>" 
	   + operator
	   + "</epl:operator>\n"
	   + "<epl:argument>\n" + argument.toXML() + "</epl:argument1>\n" 
	   + "</epl:unaryprimitiveapplication>\n";
   }
}
