package rePLcompiler;

import rVML.*;
import rePL.*;
import rePLparser.*;

import java.util.*;

public class Compiler {

    private static int TABLELIMIT = 100;

    private static Vector<INSTRUCTION> instructions;
    private static Stack<ToCompile> toCompileStack;
    private static IndexTable indexTable;
    private static boolean toplevel;

    private static Idp idp;

    private static class Idr extends Hashtable {
	private int idrc = 0;
	int idr(Vector<Association> as) {
	    Enumeration<Association> es = as.elements();
	    Vector<String> properties = new Vector<String>();
	    while (es.hasMoreElements()) {
		properties.add( ((Association) es.nextElement()).property);
	    }
	    Object[] propertiesArray = (properties.toArray());
	    Arrays.sort(propertiesArray);
	    String key = "";
	    for (int i = 0; i < propertiesArray.length; i++)
		key = key + propertiesArray[i] + ",";

	    if (containsKey(key))
		return ((Integer) get(key)).intValue();
	    else {
		put(key,new Integer(idrc));
		for (int i = 0; i < propertiesArray.length; i++) {
		    p[idrc][idp.idp((String) propertiesArray[i])] = i;
		}
		return idrc++;
	    }
	}
    }

    private static Idr idr;
    private static int[][] p;

    public static void displayInstructionArray(INSTRUCTION[] is) {
	int i = 0;
	int s = is.length;
	while (i < s) 
	    System.out.println(i + " " + is[i++]);
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
	else if (op.equals("empty"))	    { return new EMPTY(); }
	else /* (op.equals("="))    */  { return new EQUAL(); }
    }

    // compile

    public static CompilerResult compile(Expression exp) {

    	idp = new Idp();
    	toplevel = true;
    	idr = new Idr();
	p = new int[TABLELIMIT][TABLELIMIT];

	for (int i = 0; i < TABLELIMIT; i++) {
	    for (int j = 0; j < TABLELIMIT; j++) {
		p[i][j] = -1;
	    }
	}

	instructions = new Vector<INSTRUCTION>();
	toCompileStack = new Stack<ToCompile>();
	toCompileStack.push(new ToCompile(new LDF(),new IndexTable(),
					  exp.eliminateLet()));
	compileAll();

	try {

	int divisionByZeroAddress = instructions.size();

	comp(Parse.fromString("throw [DivisionByZero:true] end"),false);

	int invalidRecordAccessAddress = instructions.size();

	comp(Parse.fromString("throw [InvalidRecordAccess:true] end"),false);

	INSTRUCTION[] ia = 
	    (INSTRUCTION[]) instructions.toArray(new INSTRUCTION[1]);

	return new CompilerResult(ia,p,idp,divisionByZeroAddress,invalidRecordAccessAddress);
      
	} catch (Exception ex) {

	    INSTRUCTION[] ia = 
		(INSTRUCTION[]) instructions.toArray(new INSTRUCTION[1]);

	    System.out.println("unexpected exception "+ex+" during compilation");
	    return new CompilerResult(ia,p,idp,-1,-1);
	}

    }

    // compileAll

    public static void compileAll() {
	while (! toCompileStack.empty()) {
	    ToCompile toCompile = (ToCompile) toCompileStack.pop();
	    LDF funInstruction = toCompile.funInstruction;
	    funInstruction.ADDRESS = instructions.size();
	    indexTable = toCompile.indexTable;
	    comp(toCompile.body,true,toCompile.funVar);
	    toplevel = false;
	}
    }

    // compAll

    protected static void compAll(Vector es) {
	int i = 0; int s = es.size();
	while (i < s)
	    comp((Expression) es.elementAt(i++),false);
    }

    // compAllAssoc

    protected static void compAllAssoc(Vector as) {
	int i = 0; int s = as.size();
	while (i < s) {
	    Association a = (Association) as.elementAt(i++);
	    int j = idp.idp(a.property);
	    instructions.add(new LDCI(j));
	    comp(a.expression,false);
	}
    }

    // comp         

    protected static void comp(Expression exp,boolean insertReturn) {
	comp(exp,insertReturn,"");
    }

    protected static void comp(Expression exp,boolean insertReturn, 
			       String funVar) {

	if (exp instanceof Application) {
	    Expression op = ((Application)exp).operator;
	    comp(op,false);
	    compAll(((Application)exp).operands);
	    
		instructions.add(new 
		    CALL(((Application)exp).operands.size()));
		if (insertReturn) 
		    instructions.add(
				     toplevel 
				     ? (INSTRUCTION) new DONE() 
					 : (INSTRUCTION) new RTN());
	    }
	
	else if (exp instanceof If)
	    {
		comp(((If)exp).condition,false);
		JOF JOFinstruction = new JOF();
		instructions.add(JOFinstruction);
		comp(((If)exp).thenPart,insertReturn,funVar);
		GOTO GOTOinstruction = null;
		if (!insertReturn) {
		    GOTOinstruction = new GOTO();
		    instructions.add(GOTOinstruction);
		}
		JOFinstruction.ADDRESS = instructions.size();
		comp(((If)exp).elsePart,insertReturn,funVar);
		if (!insertReturn) 
		    GOTOinstruction.ADDRESS = instructions.size();
	    }
	else if (exp instanceof Try)
	    {
		TRY i = new TRY();
		instructions.add(i);
		comp( ((Try) exp).tryExpression,insertReturn,funVar);
		if (!insertReturn) instructions.add(new ENDTRY());
		GOTO j = new GOTO();
		if (!insertReturn) instructions.add(j);
		IndexTable oldIndexTable = indexTable;
		indexTable = (IndexTable) indexTable.clone();
		indexTable.extend(((Try)exp).exceptionVar);
		i.ADDRESS = instructions.size();
		comp( ((Try) exp).withExpression,insertReturn,funVar);
		j.ADDRESS = instructions.size();
		indexTable = oldIndexTable;
	    }

	else {

	    if (exp instanceof Variable)
		{
		    instructions.add(new 
			LD(indexTable.access(((Variable)exp).varname)));
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
			ToCompile(i,newIndexTable,((RecFun)exp).body,fun));
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
		    toCompileStack.push(new ToCompile(i,newIndexTable,((Fun)exp).body));
		}
	    else if (exp instanceof UnaryPrimitiveApplication) 
		{
		    comp(((UnaryPrimitiveApplication)exp).argument,false);
		    instructions.add(instructionFromOp(((UnaryPrimitiveApplication)exp)
						       .operator));
		}
	    else if (exp instanceof BinaryPrimitiveApplication) 
		{
		    comp(((BinaryPrimitiveApplication)exp).argument1,false);
		    comp(((BinaryPrimitiveApplication)exp).argument2,false);
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
	    else if (exp instanceof Record) 
		{
		    compAllAssoc(((Record)exp).associations);
		    instructions.add(new 
			RCD((((Record)exp).associations).size(),
			    idr.idr(((Record)exp).associations)));
		}
	    else if (exp instanceof Dot)
		{
		    comp( ((Dot) exp).expression, false);
		    instructions.add(new 
			LDCI(idp.idp(((Dot)exp).property)));
		    instructions.add(new DOT());
		}
	    else if (exp instanceof Empty)
		{
		    comp( ((Empty) exp).expression, false);
		    instructions.add(new EMPTY());
		}
	    else if (exp instanceof Hasproperty)
		{
		    comp( ((Hasproperty) exp).expression, false);
		    instructions.add(new 
			LDCI(idp.idp(((Hasproperty)exp).property)));
		    instructions.add(new HASP());
		}
	    else if (exp instanceof Throw)
		{
		    comp( ((Throw) exp).expression, false);
		    instructions.add(new THROW());
		}
	    else // (exp instanceof NotUsed)
		{ 
		    // skip
		}

	    if (insertReturn) 
		instructions.add(toplevel ? (INSTRUCTION) new DONE() 
		    : (INSTRUCTION) new RTN());
	}
    }
}
