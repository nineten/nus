package rePL;

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

    // Eliminate let in the try part and in the with part
    public Expression eliminateLet() {
	return new Try(tryExpression.eliminateLet(), 
		       exceptionVar,
		       withExpression.eliminateLet());
    }

   public String toString() {
      return "try " + tryExpression 
	  + " catch " + exceptionVar 
	  + " with "  + withExpression + " end";
   }
}
