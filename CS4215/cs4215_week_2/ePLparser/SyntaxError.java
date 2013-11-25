package ePLparser;

public class SyntaxError extends Exception {
    public int line,column;
    public String description;
    public SyntaxError(String d, int l, int c) {
	description = d;
	line = l;
	column = c;
    } 
 
   public String toString() {
       return "Syntax error: "+description+", line: "+(line+1)+", character: "+(column+1);
   }
}
