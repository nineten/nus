package cPLvm; 

import cVML.*;
import java.util.*;

public class VM extends FixedTimeSliceVM {

	public static Queue<VMThread> threadQueue = new LinkedList<VMThread>();

	// run:
	// instructionArray is the usual array of instuctions

	public static void run(INSTRUCTION[] instructionArray,
			int divisionByZeroAddress, 
			int invalidRecordAccessAddress) {

		threadQueue.add(new VMThread(0));

		// emulator loop

		while (threadQueue.size() != 0) {

			//	    System.out.println("outer loop");

			VMThread currentThread = threadQueue.remove();

			int pc = currentThread.pc;
			Stack<Value> os = currentThread.os;
			Stack<StackFrame> rts = currentThread.rts;
			Environment e = currentThread.e;

			int step = 0;

			innerloop:
				while (true) {

					if (step > timeSliceSize) {
						currentThread.save(pc,os,rts,e);
						threadQueue.add(currentThread);
						break innerloop;
					} else step = step + 1;

					INSTRUCTION i = instructionArray[pc];

					switch (i.OPCODE) {

					// pushing constants

					case OPCODES.LDCI:    
					{
						os.push(new IntValue(((LDCI)i).VALUE));
						pc++;
						break;
					}
					case OPCODES.LDCB:    
					{
						os.push(new BoolValue(((LDCB)i).VALUE));
						pc++;
						break;
					}
					case OPCODES.LDCP:    
					{
						os.push(new PropertyValue(((LDCP)i).VALUE));
						pc++;
						break;
					}
					// primitive operations:
					// watch out, the non-commutative operations have to consider
					// that the arguments appear on the stack in reverse order!
					case OPCODES.PLUS:    
					{
						os.push(new IntValue(
								((IntValue) os.pop()).value
								+ 
								((IntValue) os.pop()).value));
						pc++;
						break;
					}
					case OPCODES.TIMES: 
					{
						os.push(new IntValue(
								((IntValue) os.pop()).value
								* 
								((IntValue) os.pop()).value));
						pc++;
						break;
					}
					case OPCODES.MINUS:
					{
						os.push(new IntValue(
								- ((IntValue) os.pop()).value
								+
								((IntValue) os.pop()).value));
						pc++;
						break;
					}

					// to be improved by student
					case OPCODES.DIV:
					{
						int divisor = ((IntValue) os.pop()).value;
						os.push(new 
								IntValue(((IntValue) os.pop()).value
										/
										divisor));
						pc++;
						break;
					}
					case OPCODES.OR:
					{ 
						boolean b1, b2;
						b2 = ((BoolValue)os.pop()).value;
						b1 = ((BoolValue)os.pop()).value;
						os.push(new BoolValue(b1 || b2));
						pc++;
						break;
					}
					case OPCODES.AND:   
					{ 
						boolean b1, b2;
						b2 = ((BoolValue)os.pop()).value;
						b1 = ((BoolValue)os.pop()).value;
						os.push(new BoolValue(b1 && b2));
						pc++;
						break;
					}
					case OPCODES.NOT:
					{
						os.push(new BoolValue(
								! ((BoolValue) os.pop()).value));
						pc++;
						break;
					}
					case OPCODES.GREATER:
					{
						os.push(new BoolValue(
								((IntValue) os.pop()).value
								<
								((IntValue) os.pop()).value));
						pc++;
						break;
					}
					case OPCODES.LESS:
					{
						os.push(new BoolValue(
								((IntValue) os.pop()).value
								>
								((IntValue) os.pop()).value));
						pc++;
						break;
					}
					case OPCODES.EQUAL:
					{
						os.push(new BoolValue(
								((IntValue) os.pop()).value
								==
									((IntValue) os.pop()).value));
						pc++;
						break;
					}
					// jump by setting pc.
					case OPCODES.GOTO:
					{
						pc = ((GOTO)i).ADDRESS;
						break;
					}
					case OPCODES.JOF:
					{
						pc = (((BoolValue) os.pop()).value) 
						? pc+1
								: ((JOF)i).ADDRESS;
						break;
					}
					// load function by pushing closure.
					// environment in closure has space for arguments.
					// CALL actually fills these new slots.
					case OPCODES.LDF:
					{
						os.push(new Closure(e,((LDF)i).ADDRESS));
						pc++;
						break;
					}
					// recursive closures have an extra slot for the
					// function variable.
					// this slot is filled by the function definition. 
					// Note again the circularity via environment addressing.
					case OPCODES.LDRF:
					{
						Environment envr = e.extend(1);
						Value fv = new 
						Closure(envr,((LDRF)i).ADDRESS);
						envr.setElementAt(fv,e.size());
						os.push(fv);
						pc++;
						break;
					}
					// CALL finds a closure on top of the stack.
					// the closure environment is filled up with
					// actual parameters that are popped from the stack.
					// the registers are saved in stack frame and
					// the stack frame pushed on runtime stack.
					// new registers are initialized in the obvious way.
					case OPCODES.CALL:
					{ int n = ((CALL)i).NUMBEROFARGUMENTS;
					Closure closure 
					= (Closure) os.elementAt(os.size()-n-1);
					Environment newEnv 
					= closure.environment.extend(n);
					int s = newEnv.size();
					int k = s - n;
					int j = s - 1;
					while (j >= k) 
						newEnv.setElementAt(os.pop(),j--);
					os.pop();
					rts.push(new StackFrame(pc+1,e,os));
					pc = closure.ADDRESS;
					e = newEnv;
					os = new Stack<Value>();
					break;
					}
					// TAILCALL finds a closure on top of the stack.
					// the closure environment is filled up with
					// actual parameters that are popped from the stack.
					// the registers are saved in stack frame and
					// the stack frame pushed on runtime stack.
					// new registers are initialized in the obvious way.
					case OPCODES.TAILCALL:
					{ 
						int n = ((TAILCALL)i).NUMBEROFARGUMENTS;
						Closure closure 
						= (Closure) os.elementAt(os.size()-n-1);
						int s = e.size();
						int k = s - n;
						int j = s - 1;
						while (j >= k) 
							e.setElementAt(os.pop(),j--);
						os.pop();
						pc = closure.ADDRESS;
						break;
					}
					// address for LD is calculated by compiler and
					// put in the INDEX field of the instruction.
					// LD only needs to push the environment value under INDEX
					case OPCODES.LD:
					{
						os.push(e.elementAt(((LD)i).INDEX));
						pc++;
						break;
					}
					// RTN reinstalls the top frame of the runtime stack,
					// and pushes the return value on the operand stack.
					case OPCODES.RTN:
					{   
						if (rts.empty()) break innerloop;
						StackFrame f = rts.pop();
						pc = f.pc;
						e = f.environment;
						Value returnValue = os.pop();
						os = f.operandStack;
						os.push(returnValue);
						break;
					}
					case OPCODES.ASSIGN:
					{
						Value v = os.pop();
						int slot = ((IntValue) (os.pop())).value;
						e.set(slot,v);
						os.push(v);
						pc++;
						break;
					}

					case OPCODES.RCD:
					{
						int n = ((RCD)i).NUMBEROFARGUMENTS;
						RecordValue r = new RecordValue();
						for (int j=0; j < n; j++) {
							Value v = os.pop();
							String prop = 
								((PropertyValue)(os.pop())).value;
							r.put(prop,v);
						}
						os.push(r);
						pc++;
						break;
					}

					case OPCODES.RASSIGN: 
					{
						Value v = os.pop();
						String prop = 
							((PropertyValue)(os.pop())).value;
						RecordValue r = ((RecordValue)(os.pop()));
						r.put(prop,v);
						os.push(v);
						pc++;
						break;
					}

					// to be improved by student
					case OPCODES.DOT:
					{
						String prop = 
							((PropertyValue)(os.pop())).value;
						RecordValue v = ((RecordValue)(os.pop()));
						os.push(v.get(prop));
						pc++;
						break;
					}

					case OPCODES.HASP: 
					{
						String prop = 
							((PropertyValue)(os.pop())).value;
						RecordValue v = ((RecordValue)(os.pop()));
						os.push(new BoolValue(v.get(prop)!=null));
						pc++;
						break;
					}

					case OPCODES.EMPTY: 
					{
						RecordValue v = ((RecordValue)(os.pop()));
						os.push(new BoolValue(v.size()==0));
						pc++;
						break;
					}

					case OPCODES.PRINT: 
					{
						Value v = os.pop();
						System.out.println(v);
						os.push(v);
						pc++;
						break;
					}

					case OPCODES.POP: 
					{
						os.pop();
						pc++;
						break;
					}

					// to be implemented by student
					case OPCODES.THROW: 
					{
						pc++;
						break;
					}

					// to be implemented by student
					case OPCODES.TRY: 
					{
						pc++;
						break;
					}

					// to be implemented by student
					case OPCODES.ENDTRY: 
					{
						pc++;
						break;
					}
					
					// to be implemented by student
					case OPCODES.STARTTHREAD: 
					{
						pc++;
						break;
					}

					// to be implemented by student
					case OPCODES.ENDTHREAD: 
					{
						pc++;
						break;
					}

					// to be implemented by student
					case OPCODES.WAIT: 
					{
						pc++;
						break;
					}

					// to be implemented by student
					case OPCODES.SIGNAL: 
					{
						pc++;
						break;
					}


					// DONE simply breaks the loop. The result is now
					// on top of the operand stack

					case OPCODES.DONE:    break innerloop;

					default:              System.out.println(" unknown opcode: " 
							+ i.OPCODE);
					pc++;
					}
				}
		}
	}
}