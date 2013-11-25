package oPL;

public class BoolValue implements Value {
  public boolean value;
  public BoolValue(boolean v) {
    value = v;
  }
  public String toString() {
    return new Boolean(value).toString();
  }
}

