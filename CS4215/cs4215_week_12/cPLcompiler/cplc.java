package cPLcompiler; 

import cVML.*;
import cPL.*;
import cPLparser.*;

import java.util.*;
import java.io.*;

class cplc {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String cplfile=args[0];

        // filename for output is basename.ivml
	StringTokenizer st=new StringTokenizer(cplfile,".");
	String classname=st.nextToken();
	String cvmlfile=classname+".cvml";

	try {
	    // parse cpl expression

	    String prologue = 
		"let lookupInClass = recfun lookupInClass theClass methodname -> " + 
		"if theClass hasproperty methodname then " + 
		"theClass.methodname " + 
		"else (lookupInClass theClass.Parent methodname) end end in " + 
		"let lookup = fun object methodname -> " + 
		"(lookupInClass object.Class methodname) end " + 
		"new = fun theClass -> [Class:theClass] end in ";

	    String epilogue = " end end";

	    Expression cpl=Parse.fromFileName(prologue,epilogue,cplfile);

	    try {	    
		// create object output stream
		ObjectOutputStream oos 
		    = new 
		    ObjectOutputStream(new 
				       FileOutputStream(cvmlfile));
		    
		// and write resulting 
		// instruction array to output stream

		CompilerResult cr = Compiler.compile(cpl,false,true);
		// false,true: not optimized and verbose
		oos.writeObject(cr);

		//				Compiler.displayInstructionArray(cr.instructionArray);

		oos.close();
	    
		// indicate successful compilation to user
		System.out.println("cvml code written to "+
				       cvmlfile);
	    }
	    catch (Exception ex) {
		System.out.println("\ncannot write virtual machine code "+
				   ex);
	    }
	}		

	catch (Exception e) {
	    System.out.println(e);
	}
    }
}