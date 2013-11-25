package cPLvm;

public class Closure implements Value {
  public Environment environment;
  public int ADDRESS;
  public Closure(Environment e, int a) {
    environment = e;
    ADDRESS = a;
  }
}
