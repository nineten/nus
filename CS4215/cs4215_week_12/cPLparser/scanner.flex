/* JFlex example: part of Java 1.0/1.1 language lexer specification */

package cPLparser;

import cPL.*;
import java_cup.runtime.*;

%%

 

%class scanner
%unicode
%cup
%line

%column

%yylexthrow SyntaxError

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

Identifier = [:lowercase:] [:jletterdigit:]*

Path = [^']*

Property = [:uppercase:] [:jletterdigit:]*

DecIntegerLiteral = "-"? ( 0 | [1-9][0-9]* )


%state STRING


%%

 

/* keywords */

"fun"              { return symbol(sym.FUN); }
"end"              { return symbol(sym.END); }
"recfun"           { return symbol(sym.RECFUN); }
"->"               { return symbol(sym.ARROW); }
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
"if"               { return symbol(sym.IF); }
"then"             { return symbol(sym.THEN); }
"else"             { return symbol(sym.ELSE); }
"let"              { return symbol(sym.LET); }
"in"               { return symbol(sym.IN); }
"true"             { return symbol(sym.TRUE); }
"false"            { return symbol(sym.FALSE); }

"["                { return symbol(sym.LBRACKET); }
"]"                { return symbol(sym.RBRACKET); }
"::"               { return symbol(sym.PAIR); }
":"                { return symbol(sym.COLON); }
","                { return symbol(sym.COMMA); }
"."                { return symbol(sym.DOT); }
"empty"            { return symbol(sym.EMPTY); }
"print"            { return symbol(sym.PRINT); }
"hasproperty"      { return symbol(sym.HASPROPERTY); }
"try"              { return symbol(sym.TRY); }
"catch"            { return symbol(sym.CATCH); }
"with"             { return symbol(sym.WITH); }
"throw"            { return symbol(sym.THROW); }
"thread"           { return symbol(sym.THREAD); }
"wait"             { return symbol(sym.WAIT); }
"signal"           { return symbol(sym.SIGNAL); }

"while"            { return symbol(sym.WHILE); }
"do"               { return symbol(sym.DO); }
";"                { return symbol(sym.SEMICOLON); }
":="               { return symbol(sym.ASSIGN); }

"class"            { return symbol(sym.CLASS); }
"end"              { return symbol(sym.END); }
"method"           { return symbol(sym.METHOD); }
"extends"          { return symbol(sym.EXTENDS); }

/* identifier */
{Identifier}       { return symbol(sym.IDENTIFIER,yytext()); }

/* properties */ 
{Property}         { return symbol(sym.PROPERTY,yytext()); }

'{Path}'     { String text=yytext(); 
		     return symbol(sym.PROPERTY,
				   text.substring(1,text.length()-1));
                   }

/* integer */ 
{DecIntegerLiteral} { 
		     int x = 0;
                     try { x = Integer.parseInt(yytext()); } 
                     catch(NumberFormatException nfe) { 
                        System.out.println("wrong int format; "+
                                           "internal error in scanner"); } 
                     return symbol(sym.INTEGER,new Integer(x)); 
                   }
 
/* comments */
{Comment}          { /* ignore */ }
 
/* whitespace */
{WhiteSpace}       { /* ignore */ }

/* error fallback */
.|\n               { throw new SyntaxError("Illegal character \""+
                                           yytext()+"\"",
                                           yyline,yycolumn); 
	   	   }
