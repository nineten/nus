FILENAMES = simpl simplc Value BoolValue IntValue Closure Environment StackFrame ToCompile Compiler VM 

CLASSFILES = $(FILENAMES:%=%.class)

all: $(CLASSFILES)

%.class: %.java
	javac $?

clean:
	rm *.class *~
