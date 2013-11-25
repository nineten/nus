FILENAMES = simpl Evaluator 

CLASSFILES = $(FILENAMES:%=%.class)

all: $(CLASSFILES)

%.class: %.java
	javac $?

clean:
	rm *.class *~
