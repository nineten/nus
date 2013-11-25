package eVM; 

import ePL.*;
import eVML.*;
import ePLparser.*;

import java.util.*;
import java.io.*;

class eplc {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String eplfile=args[0];

        // filename for output is basename.evml
	StringTokenizer st=new StringTokenizer(eplfile,".");
	String classname=st.nextToken();
	String evmlfile=classname+".evml";

	try {
	    // parse ePL expression
	    Expression epl=Parse.fromFileName(eplfile);

	    INSTRUCTION[] ia = new Compiler(epl).compile();
		
	    try {	    
		// create object output stream
		ObjectOutputStream oos 
		    = new 
		    ObjectOutputStream(new 
				       FileOutputStream(evmlfile));
		    
		// and write resulting 
		// instruction array to output stream
		
		oos.writeObject(ia);
		oos.close();
	    
		// indicate successful compilation to user
		System.out.println("eVML code written to "+
				       evmlfile);
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
		FileReader fr = new FileReader(eplfile); 
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