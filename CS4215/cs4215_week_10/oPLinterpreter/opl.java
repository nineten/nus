package oPLinterpreter;

import oPL.*;
import oPLparser.*;

class opl {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String oplfile=args[0];

	try {
	    // parse oPL expression, including the "wrapper"
	    Expression opl=Parse.fromFileName(Wrapper.prologue,Wrapper.epilogue,oplfile);
		
	    System.out.println("Result of evaluation:\n"+
			       opl.eval(new Environment()));
	    System.exit(1);
	}

	catch (Exception e) {
	    System.out.println(e);
	}
    }
}
