package simPLvm;

import sVML.*;

import java.util.*;

public class VM {

	// run

	public static Value run(INSTRUCTION[] instructionArray) {

		// initialize registers

		int pc = 0;
		Stack<Value> os = new Stack<Value>();
		Stack<StackFrame> rts = new Stack<StackFrame>();
		Environment e = new Environment();

		// loop

		loop: while (true) {
			INSTRUCTION i = instructionArray[pc];

			// System.out.println("pc: "+pc+"; instruction: "+i);
			System.out.println(i + " : " + os);

			switch (i.OPCODE) {

			// pushing constants
			case OPCODES.LDCI: {
				os.push(new IntValue(((LDCI) i).VALUE));
				pc++;
				break;
			}

			case OPCODES.LDCB: {
				os.push(new BoolValue(((LDCB) i).VALUE));
				pc++;
				break;
			}

			case OPCODES.LD: {
				os.push(e.elementAt(((LD) i).INDEX));
				pc++;
				break;
			}

			case OPCODES.LDF: {
				Environment env = e;
				os.push(new Closure(env, ((LDF) i).ADDRESS));
				pc++;
				break;
			}

			case OPCODES.LDRF: {
				Environment envr = e.extend(1);
				Value fv = new Closure(envr, ((LDRF) i).ADDRESS);
				envr.setElementAt(fv, e.size());
				os.push(fv);
				pc++;
				break;
			}
				// primitive operations:
				// watch out, the non-commutative operations have to consider
				// that the arguments appear on the stack in reverse order!

			case OPCODES.PLUS: {
				os.push(new IntValue(((IntValue) os.pop()).value
						+ ((IntValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.TIMES: {
				os.push(new IntValue(((IntValue) os.pop()).value
						* ((IntValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.MINUS: {
				os.push(new IntValue(-((IntValue) os.pop()).value
						+ ((IntValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.DIV:
				int divisor = ((IntValue) os.pop()).value;
				if (divisor == 0) {
					os.push(new ErrorValue());
					break loop;
				} else {
					os.push(new IntValue(((IntValue) os.pop()).value / divisor));
					pc++;
					break;
				}

			case OPCODES.OR: {
				boolean b1, b2;
				b2 = ((BoolValue) os.pop()).value;
				b1 = ((BoolValue) os.pop()).value;
				os.push(new BoolValue(b1 || b2));
				pc++;
				break;
			}

			case OPCODES.AND: {
				boolean b1, b2;
				b2 = ((BoolValue) os.pop()).value;
				b1 = ((BoolValue) os.pop()).value;
				os.push(new BoolValue(b1 && b2));
				pc++;
				break;
			}

			case OPCODES.NOT: {
				os.push(new BoolValue(!((BoolValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.GREATER: {
				os.push(new BoolValue(
						((IntValue) os.pop()).value < ((IntValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.LESS: {
				os.push(new BoolValue(
						((IntValue) os.pop()).value > ((IntValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.EQUAL: {
				os.push(new BoolValue(
						((IntValue) os.pop()).value == ((IntValue) os.pop()).value));
				pc++;
				break;
			}

			case OPCODES.GOTO: {
				pc = ((GOTO) i).ADDRESS;
				break;
			}

			case OPCODES.JOF: {
				pc = (((BoolValue) os.pop()).value) ? pc + 1
						: ((JOF) i).ADDRESS;
				break;
			}

			case OPCODES.CALL: {
				int n = ((CALL) i).NUMBEROFARGUMENTS;
				Closure closure = (Closure) (os.elementAt(os.size() - n - 1));
				Environment newEnv = closure.environment.extend(n);
				int s = newEnv.size();
				for (int j = s - 1; j >= s - n; --j)
					newEnv.setElementAt(os.pop(), j);
				os.pop(); // function value
				rts.push(new StackFrame(pc + 1, e, os));
				pc = closure.ADDRESS;
				e = newEnv;
				os = new Stack();
				break;
			}

			case OPCODES.RTN: {
				Value returnValue = os.pop();
				StackFrame f = rts.pop();
				pc = f.pc;
				e = f.environment;
				os = f.operandStack;
				os.push(returnValue);
				break;
			}

				// DONE simply breaks the loop. The result is now
				// on top of the operand stack

			case OPCODES.DONE: {
				break loop;
			}

			default: {
				System.out.println(" unknown opcode: " + i.OPCODE);
				pc++;
			}
			}
		}
		return (Value) os.pop();
	}
}
