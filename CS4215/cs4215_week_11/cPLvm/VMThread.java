package cPLvm;

import java.util.*;

public class VMThread {
    public int pc;
    public Stack<Value> os;
    public Stack<StackFrame> rts;
    public Environment e;
    public VMThread(int p) {
      pc = p;
      os = new Stack<Value>();
      rts = new Stack<StackFrame>();
      e = new Environment();
    }
    public VMThread(int p,Environment env) {
      pc = p;
      os = new Stack<Value>();
      rts = new Stack<StackFrame>();
      e = env;
    }
    public void save(int p, Stack<Value> o, Stack<StackFrame> r, 
		     Environment env) {
      pc = p;
      os = o;
      rts = r;
      e = env;
    }
}