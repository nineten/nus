FILENAMES = Fun Application Variable Expression BoolConstant If IntConstant Let UnaryPrimitiveApplication BinaryPrimitiveApplication RecFun LetDefinition IntType BoolType FunType Type StringSet IsValue TypeError TypeEnvironment Value IntValue BoolValue FunValue

CLASSFILES = $(FILENAMES:%=%.class)

all: $(CLASSFILES)

%.class: %.java
	javac $?

clean:
	rm *.class *~
