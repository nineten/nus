FILENAMES = epl eplc Value BoolValue IntValue Compiler VM RuggedVM

CLASSFILES = $(FILENAMES:%=%.class)

all: $(CLASSFILES)

%.class: %.java
	javac $?

clean:
	rm *.class *~
