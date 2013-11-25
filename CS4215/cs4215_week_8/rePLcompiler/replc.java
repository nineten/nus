package rePLcompiler; 

import rePL.*;
import rVML.*;
import rePLparser.*;

import java.util.*;
import java.io.*;

class replc {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String replfile=args[0];

        // filename for output is basename.rvml
	StringTokenizer st=new StringTokenizer(replfile,".");
	String classname=st.nextToken();
	String rvmlfile=classname+".rvml";

	try {
	    // parse repl expression
	    Expression repl=Parse.fromFileName(replfile);

	    try {	    
		// create object output stream
		ObjectOutputStream oos 
		    = new 
		    ObjectOutputStream(new 
				       FileOutputStream(rvmlfile));
		    
		// and write resulting 
		// instruction array to output stream
		
		CompilerResult cr = Compiler.compile(repl);

		oos.writeObject(cr);

		Compiler.displayInstructionArray(cr.instructionArray);

		oos.close();
	    
		// indicate successful compilation to user
		System.out.println("rvml code written to "+
				       rvmlfile);
	    }
	    catch (Exception ex) {
		System.out.println("\ncannot write virtual machine code "+
				   ex);
	    }
	}		

	// Lexical errors are treated nicely, giving an
	// error message with line and character of the
	// error (unfortunately, parse errors do not enjoy
	// such treatment yet)
	catch (SyntaxError e) {
	    try {
		System.out.println(e);
		FileReader fr = new FileReader(replfile); 
		BufferedReader br = new BufferedReader(fr);
		String record = null; 
		int line = e.line;
		int column = e.column;
		int lineCount = 0;
		int columnCount = 0;
		while ( (record=br.readLine()) != null & lineCount++ < line) ;
		System.out.println(record);
		while ( columnCount++ != column ) 
		    System.out.print(" ");
		System.out.println("^");
	    } catch (Exception ex) {
		System.out.println(e);
	    }
	}
	catch (FileNotFoundException e) {
	    System.out.println(e);
	}
	catch (Exception e) {
	    System.out.println(e);
	}
    }
}