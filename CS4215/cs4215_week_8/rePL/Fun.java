package rePL;

import java.util.*;

public class Fun implements Expression {

    public Vector<String> formals;
    public Expression body;

    public Fun(Vector<String> xs, Expression b) {
       formals = xs;
       body = b;
    }

    public Expression eliminateLet() {
	return new Fun(formals, body.eliminateLet());
    }

    // //////////////////////
    // Support Functions
    // //////////////////////

   public String toString() {
      String s = "";
      for (String f : formals)
	  s = s + " " + f;
      return "fun" + 
	  s + " -> " + body + " end";
   }
}
