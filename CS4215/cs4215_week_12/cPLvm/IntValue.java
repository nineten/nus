package cPLvm;

public class IntValue implements Value {
  public int value;
  public IntValue(int v) {
    value = v;
  }
  public String toString() {
    return new Integer(value).toString();
  }
}

