package cPLcompiler;

import cVML.*;
import cPL.*;
import cPLparser.*;

import java.util.*;

public class Compiler {

    private static Vector<INSTRUCTION> instructions;
    private static Stack<ToCompile> toCompileStack;
    private static IndexTable indexTable;
    private static boolean optimized = false;

    public static void displayInstructionArray(INSTRUCTION[] is) {
	int i = 0;
	int s = is.length;
	while (i < s) 
	    System.out.println(i + " " + is[i]+"; opcode: "+is[i++].OPCODE);
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
	else if (op.equals("="))        { return new EQUAL(); }
	else if (op.equals("empty"))    { return new EMPTY(); }
	else if (op.equals("print"))    { return new PRINT(); }
	else if (op.equals("hasproperty")) { return new HASP(); }
	else /* if (op.equals(".")) */  { return new DOT(); }
    }

    // compile

    public static CompilerResult compile(Expression exp,
					 boolean opt,boolean verb) {

	instructions = new Vector<INSTRUCTION>();
	toCompileStack = new Stack<ToCompile>();
	toCompileStack.push(new ToCompile(new TOPLEVEL(),new IndexTable(),
					                 exp,"",new DONE()));

	
	optimized = opt;

	compileAll();

	try {

	int divisionByZeroAddress = instructions.size();

	comp(Parse.fromString("throw [DivisionByZero:true] end"),null);

	int invalidRecordAccessAddress = instructions.size();

	comp(Parse.fromString("throw [InvalidRecordAccess:true] end"),null);

	INSTRUCTION[] ia = 
	    (INSTRUCTION[]) instructions.toArray(new INSTRUCTION[1]);

        if (verb) Compiler.displayInstructionArray(ia);
		
	return new CompilerResult(ia,divisionByZeroAddress,invalidRecordAccessAddress);

	} catch (Exception ex) {

	    INSTRUCTION[] ia = 
		(INSTRUCTION[]) instructions.toArray(new INSTRUCTION[1]);

	    System.out.println("unexpected exception "+ex+" during compilation");
	    return new CompilerResult(ia,-1,-1);
	}

    }

    // compileAll

    public static void compileAll() {
	while (! toCompileStack.empty()) {
	    ToCompile toCompile = (ToCompile) toCompileStack.pop();
	    ADDRESS_INSTRUCTION addressInstruction 
		= toCompile.addressInstruction;
	    addressInstruction.ADDRESS = instructions.size();
	    indexTable = toCompile.indexTable;
	    comp(toCompile.body,
             toCompile.toInsert, toCompile.funVar);
	}
    }

    // compAll

    protected static void compAll(Vector es) {
	int i = 0; int s = es.size();
	while (i < s)
	    comp((Expression) es.elementAt(i++),null);
    }

    // compAllAssoc

    protected static void compAllAssoc(Vector<Association> as) {
	int i = 0; int s = as.size();
	while (i < s) {
	    Association a = as.elementAt(i++);
	    instructions.add(new LDCP(a.property));
	    comp(a.expression,null);
	}
    }

    // comp         

    protected static void comp(Expression exp,INSTRUCTION toInsert) {
	comp(exp,toInsert,"");
    }

    protected static void comp(Expression exp, INSTRUCTION toInsert, 
			       String funVar) {

	if (exp instanceof Let) {
	    Vector<Expression> arguments = new Vector<Expression>();
	    Vector<String> formals = new Vector<String>();
	    for (LetDefinition d : ((Let)exp).definitions) {
		formals.add(d.variable);
		arguments.add(d.rightHandExpression);
	    }
	    comp(new Application(new Fun(formals,((Let)exp).body),
				 arguments),
		 toInsert,funVar);
	}

	else if (exp instanceof Sequence)
	    {
		comp(((Sequence)exp).firstPart,null);
		POP POPinstruction = new POP();
		instructions.add(POPinstruction);
		comp(((Sequence)exp).secondPart,toInsert);
	    }
	
	else if (exp instanceof If)
	    {
		comp(((If)exp).condition,null);
		JOF JOFinstruction = new JOF();
		instructions.add(JOFinstruction);
		comp(((If)exp).thenPart,toInsert,funVar);
		GOTO GOTOinstruction = null;
		if (toInsert==null) {
		    GOTOinstruction = new GOTO();
		    instructions.add(GOTOinstruction);
		}
		JOFinstruction.ADDRESS = instructions.size();
		comp(((If)exp).elsePart,toInsert,funVar);
		if (toInsert==null) 
		    GOTOinstruction.ADDRESS = instructions.size();
	    }
	else if (exp instanceof Try)
	    {
		TRY i = new TRY();
		instructions.add(i);
		comp( ((Try) exp).tryExpression,toInsert,funVar);
		if (toInsert==null) {
			instructions.add(new ENDTRY());
			toInsert = new GOTO(instructions.size());
		}
		IndexTable newIndexTable = (IndexTable) indexTable.clone();
		newIndexTable.extend(((Try)exp).exceptionVar);
		toCompileStack.push(new
		    ToCompile(i,newIndexTable,((Try) exp).withExpression,"",toInsert));
	    }

	else {

	    if (exp instanceof Variable)
		{
		    instructions.add(new 
			LD(indexTable.access(((Variable)exp).varname)));
		}
	    else if (exp instanceof LookupApplication) {
		Expression op = ((LookupApplication)exp).operator;
		if (!optimized) comp(op,null);
		compAll(((LookupApplication)exp).operands);
		if (optimized) {
		    instructions.add(new LOOKUP());
		}
		else
		    instructions.add(
 		       new CALL(((LookupApplication)exp).operands.size()));
	    }
	    else if (exp instanceof Application) {
		Expression op = ((Application)exp).operator;
		comp(op,null);
		compAll(((Application)exp).operands);
		instructions.add(new 
				     CALL(((Application)exp).operands.size()));
	    }

	    else if (exp instanceof RecFun)
		{
		    Vector formals = ((RecFun)exp).formals;
		    LDRF i = new LDRF(formals.size());
		    instructions.add(i);
		    Enumeration e = formals.elements();
		    IndexTable newIndexTable = (IndexTable) indexTable.clone();
		    String fun = ((RecFun)exp).funVar;
		    newIndexTable.extend(fun);
		    while (e.hasMoreElements()) 
			newIndexTable.extend((String) e.nextElement());
		    toCompileStack.push(new 
			ToCompile(i,newIndexTable,((RecFun)exp).body,fun,new RTN()));
		}
	    else if (exp instanceof Fun)
		{
		    Vector formals = ((Fun)exp).formals;
		    LDF i = new LDF(formals.size());
		    instructions.add(i);
		    Enumeration e = formals.elements();
		    IndexTable newIndexTable = (IndexTable) indexTable.clone();
		    while (e.hasMoreElements()) 
			newIndexTable.extend((String) e.nextElement());
		    toCompileStack.push(new ToCompile(i,newIndexTable,((Fun)exp).body,"",new RTN()));
		}
	    else if (exp instanceof UnaryPrimitiveApplication) 
		{
		    comp(((UnaryPrimitiveApplication)exp).argument,null);
		    instructions.add(instructionFromOp(((UnaryPrimitiveApplication)exp)
						       .operator));
		}
	    else if (exp instanceof BinaryPrimitiveApplication) 
		{
		    comp(((BinaryPrimitiveApplication)exp).argument1,null);
		    comp(((BinaryPrimitiveApplication)exp).argument2,null);
		    instructions.add(instructionFromOp(((BinaryPrimitiveApplication)exp)
						       .operator));
		}
	    else if (exp instanceof BoolConstant)
		{
		    instructions.add(new 
			LDCB(((BoolConstant)exp).value));
		}
	    else if (exp instanceof IntConstant)
		{
		    instructions.add(new 
			LDCI(((IntConstant)exp).value));
		}
	    else if (exp instanceof PropertyConstant)
		{
		    instructions.add(new 
			LDCP(((PropertyConstant)exp).value));
		}
	    else if (exp instanceof Record) 
		{
		    compAllAssoc(((Record)exp).associations);
		    instructions.add(new 
				     RCD((((Record)exp).associations).size()));
		}
	    else if (exp instanceof Throw)
		{
		    comp( ((Throw) exp).expression, null);
		    instructions.add(new THROW());
		}
	    else if (exp instanceof Assignment)
		{
		    instructions.add(new 
				     LDCI(
					  indexTable.access(((Assignment)exp)
							    .leftHandSide)));
		    comp( ((Assignment) exp).rightHandSide,null);
		    instructions.add(new ASSIGN());
		}
	    else if (exp instanceof RecordAssignment)
		{
		    comp( ((RecordAssignment) exp).recordExpression,null);
		    comp( ((RecordAssignment) exp).property,null);
		    comp( ((RecordAssignment) exp).rightHandSide,null);
		    instructions.add(new RASSIGN());
		}

	    else if (exp instanceof While)
		{
		    int testAddress = instructions.size();
		    comp(((While)exp).test,null);
		    JOF JOFinstruction = new JOF();
		    instructions.add(JOFinstruction);
		    comp(((While)exp).body,null);
		    instructions.add(new POP());
		    instructions.add(new GOTO(testAddress));
		    JOFinstruction.ADDRESS = instructions.size();
		    instructions.add(new LDCB(true));
		}
	    else if (exp instanceof ThreadExpression)
		{
		    ADDRESS_INSTRUCTION i = new STARTTHREAD();
		    instructions.add(i);
		    toCompileStack.push(
			   new ToCompile(i,indexTable,
					 ((ThreadExpression) exp).expression,"",new ENDTHREAD()));
		}
	    else if (exp instanceof Wait)
		{
		    instructions.add(new 
			  WAIT(indexTable.access(((Wait)exp).varname)));
		}
	    else if (exp instanceof Signal)
		{
		    instructions.add(new 
			  SIGNAL(indexTable.access(((Signal)exp).varname)));
		}

	    else // (exp instanceof NotUsed)
		{ 
		    // skip
		}

	    if (toInsert!=null) 
	    	instructions.add(toInsert);
	}
}
}
