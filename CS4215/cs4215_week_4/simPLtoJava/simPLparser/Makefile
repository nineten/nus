FILENAMES = parser scanner Parse SyntaxError 

CLASSFILES = $(FILENAMES:%=%.class)

all: $(CLASSFILES)

scanner.java: scanner.flex
	java JFlex.Main scanner.flex

parser.java: parser.cup
	java java_cup.Main < parser.cup

Parse.class: Parse.java parser.class
	javac Parse.java

%.class: %.java
	javac $?

clean:
	rm *.class scanner.java parser.java *~

