package simPLdynamic;

import simPL.*;
import simPLparser.*;

import java.io.*;

class simpl2 {

	static public void main(String[] args) 
	{

		for(int i = 0; i < args.length; i++) {

		
		// read name of source file from command line
		String simplfile=args[i];

		try {
			// parse simPL expression
			// and eliminate all let expressions
			Expression simpl=Parse.fromFileName(simplfile).eliminateLet();

			try {
			// evaluate the expression using oneStep 
			System.out.println("Result of evaluation"+(i+1)+": "+
					Evaluator.eval(simpl));
			} catch (StackOverflowError e) {
				System.out.println("your program goes in an infinite loop");
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
			}
			catch (Exception ex) {
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

}