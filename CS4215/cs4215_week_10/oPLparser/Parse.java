package oPLparser;

import oPL.*;

import java.io.*;

public class Parse {
	public static Expression fromFileName(String prologue, String epilogue, String filename) {

		String content = "";
		Expression result;

		try {

			FileInputStream infile = new FileInputStream(filename);
			BufferedReader filereader
			= new BufferedReader(new InputStreamReader(infile));

			String nextline = "";
			while (nextline != null) {
				content = content + "\n" + nextline;
				nextline = filereader.readLine();
			}
		}
		catch (Exception e) {
			System.out.println("error while reading input");
			result = new Variable("error");
		};

		try {
			String allofit = prologue+content+epilogue;
			parser p = new parser(new scanner(new StringReader(allofit)));
			result = (Expression) p.parse().value;
		}
		catch (Exception e) {
			System.out.println("parse error");
			result = new Variable("error");
		};
		return result;
	}

	public static Expression fromString(String s) 
	throws SyntaxError,Exception {
		parser p = new parser(new scanner(new StringReader(s)));
		return (Expression) p.parse().value;
	}
}
