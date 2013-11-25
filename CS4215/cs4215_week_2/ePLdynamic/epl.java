package ePLdynamic;

import ePL.*;
import ePLparser.*;

import java.io.*;

class epl  {

    static public void main(String[] args) 
    {

	// read name of source file from command line
	String eplfile=args[0];

	try {
	    // parse ePL expression
	    Expression epl=Parse.fromFileName(eplfile);

		System.out.println("Result of evaluation: "+
				   Evaluator.evaluate(epl));
	 
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
