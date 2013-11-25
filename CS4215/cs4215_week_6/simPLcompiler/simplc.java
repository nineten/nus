package simPLcompiler; 

import simPL.*;
import sVML.*;
import simPLparser.*;

import java.util.*;
import java.io.*;

class simplc {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String simplfile=args[0];

        // filename for output is basename.svml
	StringTokenizer st=new StringTokenizer(simplfile,".");
	String classname=st.nextToken();
	String svmlfile=classname+".svml";

	try {
	    // parse simPL expression
	    Expression simpl=Parse.fromFileName(simplfile).eliminateLet();

	    try {	    
		simpl.check(new TypeEnvironment());
	    }
	    catch (TypeError ex) {
		System.out.println("type error; cannot compile "+simplfile);
		System.exit(1);
	    }
		
	    INSTRUCTION[] ia = Compiler.compile(simpl);

	    //    Compiler.displayInstructionArray(ia);

	    try {	    
		// create object output stream
		ObjectOutputStream oos 
		    = new 
		    ObjectOutputStream(new 
				       FileOutputStream(svmlfile));
		    
		// and write resulting 
		// instruction array to output stream
		
		oos.writeObject(ia);
		oos.close();
	    
		// indicate successful compilation to user
		System.out.println("sVML code written to "+
				       svmlfile);
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
		FileReader fr = new FileReader(simplfile); 
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