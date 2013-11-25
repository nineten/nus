package simPLcompiler;

import simPL.*;
import sVML.*;

import java.util.*;

public class Compiler {

    private static Vector<INSTRUCTION> instructions;
    private static Stack<ToCompile> toCompileStack;
    private static IndexTable indexTable;
    private static boolean toplevel = true;

    public static void displayInstructionArray(INSTRUCTION[] is) {
	int k = 0;
      for (INSTRUCTION i : is)
	  System.out.println(k++ + " " + i);
    }

    private static INSTRUCTION instructionFromOp(String op) {
	if      (op.equals("+"))        { return new PLUS(); }
	else if (op.equals("-"))        { return new MINUS(); }
	else if (op.equals("*"))        { return new TIMES(); }
	else if (op.equals("/"))        { return new DIV(); }
	else if (op.equals("\\"))       { return new NOT(); }
	else if (op.equals("&"))        { return new AND(); }
	else if (op.equals("|"))        { return new OR(); }
	else if (op.equals("<"))        { return new LESS(); }
	else if (op.equals(">"))        { return new GREATER(); }
	else /* (op.equals("="))    */  { return new EQUAL(); }
    }

    // compile

    public static INSTRUCTION[] compile(Expression exp) {
	instructions = new Vector<INSTRUCTION>();
	START STARTinstruction = new START();
	LDF LDFinstruction = new LDF();
	instructions.add(STARTinstruction);
	toCompileStack = new Stack<ToCompile>();
	toCompileStack.push(new ToCompile(LDFinstruction,new IndexTable(),
					  exp.eliminateLet()));
	compileAll();
	STARTinstruction.MAXSTACKSIZE = LDFinstruction.MAXSTACKSIZE; 
	return (INSTRUCTION[]) (instructions.toArray(new INSTRUCTION[1]));
    }

    // compileAll

    public static void compileAll() {
	while (! toCompileStack.empty()) {
	    ToCompile toCompile = (ToCompile) toCompileStack.pop();
	    LDF funInstruction = toCompile.funInstruction;
	    funInstruction.ADDRESS = instructions.size();
	    indexTable = toCompile.indexTable;
	    funInstruction.MAXSTACKSIZE 
		= comp(toCompile.body,true,toCompile.funVar);
	    toplevel = false;
	}
    }

    // compAll

    protected static int compAll(Vector<Expression> es) {
	int i = 0; int s = es.size();
	int maxstack = 0;
	while (i < s) 
	    maxstack = Math.max(i+comp((Expression) es.elementAt(i++),false),
				maxstack);
	return maxstack;
    }

    // comp         

    protected static int comp(Expression exp,boolean insertReturn) {
	return comp(exp,insertReturn,"");
    }

    protected static int comp(Expression exp,boolean insertReturn, 
                              String funVar) {

	int maxstack = 0;

	if (exp instanceof Application) {
	    Expression op = ((Application)exp).operator;
	    int maxstackoperator = comp(op,false);
	    int maxstackoperands = compAll(((Application)exp).operands);
	    instructions.add(new 
			     CALL(((Application)exp).operands.size()));
	    if (insertReturn) 
		instructions.add(
				 toplevel 
				 ? (INSTRUCTION) new DONE() 
				 : (INSTRUCTION) new RTN());
	    maxstack = Math.max(maxstackoperator,maxstackoperands+1);
	}
	else if (exp instanceof If)
	    {
		int maxstackcondition=comp(((If)exp).condition,false);
		JOF JOFinstruction = new JOF();
		instructions.add(JOFinstruction);
		int maxstackthen=comp(((If)exp).thenPart,insertReturn,funVar);
		GOTO GOTOinstruction = null;
		if (!insertReturn) {
		    GOTOinstruction = new GOTO();
		    instructions.add(GOTOinstruction);
		}
		JOFinstruction.ADDRESS = instructions.size();
		int maxstackelse=comp(((If)exp).elsePart,insertReturn,funVar);
		if (!insertReturn) 
		    GOTOinstruction.ADDRESS = instructions.size();
		maxstack = Math.max(Math.max(maxstackcondition,
					     maxstackthen),
				    maxstackelse);
	    }

	else {

	    if (exp instanceof Variable)
		{
		    instructions.add(new 
				     LD(indexTable.access(((Variable)exp).varname)));
		    maxstack = 1;
		}
	    else if (exp instanceof RecFun)
		{
		    Vector<String> formals = ((RecFun)exp).formals;
		    LDRF i = new LDRF();
		    instructions.add(i);
		    Enumeration<String> e = formals.elements();
		    IndexTable newIndexTable = (IndexTable) indexTable.clone();
		    String fun = ((RecFun)exp).funVar;
		    newIndexTable.extend(fun);
		    while (e.hasMoreElements()) 
			newIndexTable.extend((String) e.nextElement());
		    toCompileStack.push(new 
					ToCompile(i,newIndexTable,((RecFun)exp).body,fun));
		    maxstack = 1;
		}
	    else if (exp instanceof Fun)
		{
		    Vector<String> formals = ((Fun)exp).formals;
		    LDF i = new LDF();
		    instructions.add(i);
		    Enumeration<String> e = formals.elements();
		    IndexTable newIndexTable = (IndexTable) indexTable.clone();
		    while (e.hasMoreElements()) 
			newIndexTable.extend((String) e.nextElement());
		    toCompileStack.push(new ToCompile(i,newIndexTable,((Fun)exp).body));
		    maxstack = 1;
		}
	    else if (exp instanceof UnaryPrimitiveApplication) 
		{
		    maxstack = comp(((UnaryPrimitiveApplication)exp).argument,
				    false);
		    instructions
			.add(instructionFromOp(((UnaryPrimitiveApplication)exp)
					       .operator));
		}
	    else if (exp instanceof BinaryPrimitiveApplication) 
		{
		    int maxstack1 
			= comp(((BinaryPrimitiveApplication)exp).argument1,
			       false);
		    int maxstack2
			= comp(((BinaryPrimitiveApplication)exp).argument2,
			       false);
		    instructions
			.add(instructionFromOp(((BinaryPrimitiveApplication)exp)
					       .operator));
		    maxstack = Math.max(maxstack1,1+maxstack2);
		}
	    else if (exp instanceof BoolConstant)
		{
		    instructions.add(new 
				     LDCB(((BoolConstant)exp).value));
		    maxstack = 1;
		}
	    else if (exp instanceof IntConstant)
		{
		    instructions.add(new 
				     LDCI(((IntConstant)exp).value));
		    maxstack = 1;
		}
	    else // (exp instanceof NotUsed)
		{ 
		    // skip
		}

	    if (insertReturn) 
		instructions.add(toplevel ? (INSTRUCTION) new DONE() 
				 : (INSTRUCTION) new RTN());
	}
        return maxstack;
    }
}
