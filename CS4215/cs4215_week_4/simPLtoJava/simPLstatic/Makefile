FILENAMES = simpl 

CLASSFILES = $(FILENAMES:%=%.class)

all: $(CLASSFILES)

%.class: %.java
	javac $?

clean:
	rm *.class *~
