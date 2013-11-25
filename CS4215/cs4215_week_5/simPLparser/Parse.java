package simPLparser;

import simPL.*;

import java.io.*;

public class Parse {
   public static Expression fromFileName(String filename) 
       throws SyntaxError,FileNotFoundException,Exception {
     parser p = new parser(new scanner(new FileReader(filename)));
     return (Expression) p.parse().value;
   }

   public static Expression fromString(String s) 
       throws SyntaxError,Exception {
     parser p = new parser(new scanner(new StringReader(s)));
     return (Expression) p.parse().value;
   }
}
