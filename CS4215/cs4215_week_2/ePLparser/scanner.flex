/* JFlex specification of ePL lexical syntax */

package ePLparser;

import java_cup.runtime.*;

%%

%class scanner
%unicode
%cup
%line
%column

%yylexthrow SyntacticError

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

%}

 

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] {CommentContent} "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
DocumentationComment = "/**" {CommentContent} "*"+ "/"

CommentContent       = ( [^*] | \*+ [^/*] )*

DecIntegerLiteral = "-"? ( 0 | [1-9][0-9]* )
 
%state STRING

%%

/* keywords */

"("                { return symbol(sym.LPAREN); }
")"                { return symbol(sym.RPAREN); }
"+"                { return symbol(sym.PLUS); }
"-"                { return symbol(sym.MINUS); }
"*"                { return symbol(sym.TIMES); }
"/"                { return symbol(sym.DIV); }
"&"                { return symbol(sym.AND); }
"|"                { return symbol(sym.OR); }
"\\"               { return symbol(sym.NEG); }
"="                { return symbol(sym.EQUAL); }
"<"                { return symbol(sym.LESS); }
">"                { return symbol(sym.GREATER); }
"true"             { return symbol(sym.TRUE); }
"false"            { return symbol(sym.FALSE); }

/* integers */ 
{DecIntegerLiteral} { 
                     return symbol(sym.INTEGER,yytext()); 
                   }
 
/* comments */
{Comment}          { /* ignore */ }
 
/* whitespace */
{WhiteSpace}       { /* ignore */ }

/* error fallback */
.|\n               { throw new SyntacticError("Illegal character \""+
                                           yytext()+"\"",
                                           yyline,yycolumn); 
	   	   }
