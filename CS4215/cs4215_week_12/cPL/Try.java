package cPL;

import java.util.*;

public class Try implements Expression {
    public String exceptionVar;
    public Expression tryExpression;
    public Expression withExpression;
    public Try(Expression t, String ev, Expression w) {
	tryExpression = t;
	exceptionVar = ev;
	withExpression = w;
   }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      return "try " + tryExpression 
	  + " catch " + exceptionVar 
	  + " with "  + withExpression + " end";
   }
}
