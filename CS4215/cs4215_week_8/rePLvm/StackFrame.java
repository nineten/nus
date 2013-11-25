package rePLvm;

import java.util.*;

public class StackFrame {
  public int pc;
  public Environment environment;
  public Stack<Value> operandStack;
  public StackFrame(int p, Environment e, Stack<Value> os) {
     pc = p;
     environment = e;
     operandStack = os;
  }
}
