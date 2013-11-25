package imPL;

import java.util.*;

public class FunValue implements Value {
  public Environment environment;
  public Vector<String> formals;
  public Expression body;

  public FunValue(Environment e, Vector<String> xs, Expression b) {
    environment = e;
    formals = xs;
    body = b;
  }

  public String toString() {
      String s = "";
      for (String next : formals) 
	  s = s + " " + next;
      return "fun"+s+" -> ... end";
  }
}
