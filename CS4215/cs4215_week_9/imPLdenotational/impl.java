package imPLdenotational;

import imPL.*;
import imPLparser.*;

import java.util.*;
import java.io.*;

class impl {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String implfile=args[0];

	try {
	    // parse simPL expression
	    Expression impl=Parse.fromFileName(implfile);
		
	    System.out.println("Result of evaluation:\n"+
			       impl.eval(new Store(),new Environment()).value);
	    System.exit(1);
	}

	// Lexical errors are treated nicely, giving an
	// error message with line and character of the
	// error (unfortunately, parse errors do not enjoy
	// such treatment yet)
	catch (SyntaxError e) {
	    try {
		System.out.println(e);
		FileReader fr = new FileReader(implfile); 
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
