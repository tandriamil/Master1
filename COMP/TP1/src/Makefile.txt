# Makefile for separate lexer/parser ANTLR grammars

# use "G=" on the command line to override the grammar name (default: 'TP1').
# example: make G=TP2 (without the ".g")

# use "ARGS=" with the 'run' target to specify command-line 
# arguments.
# example : make run ARGS=tests/test1.ttl

ANTLR_JAR=/share/m1info/COMP/antlr/antlr-3.5.jar
# If "G" is not specified, it defaults to "TP1".
G?=TP1

# This is the first rule in the file. It is therefore the default if 
# none are specified.
# Running 'make' without arguments is equivalent to running 'make parser'.
# This rule depends on the compiled Java files for the lexer and the parser.
parser: $(G)Lexer.class $(G)Parser.class

# This is the rule to run the compiled parser. 
# It depends on the file with the main function (TP1.java) and on the parser.
run: parser $(G).class
	java -cp $(ANTLR_JAR):. $(G) $(ARGS)

# This "special" rule compiles each .java file into a .class file.
# Whenever a .java file is modified, this rule recompiles it.
%.class: %.java
	javac -cp $(ANTLR_JAR):. $<

# This rule updates the ANTLR-generated parser whenever the parser or the
# lexer grammars are modified.
$(G)Parser.java: $(G)Parser.g $(G)Lexer.g
	java -cp $(ANTLR_JAR):. org.antlr.Tool $(G)Parser.g

# This rule updates the ANTLR-generated lexer whenever the lexer grammar
# is modified.
$(G)Lexer.java: $(G)Lexer.g
	java -cp $(ANTLR_JAR):. org.antlr.Tool $(G)Lexer.g

# This rule removes intermediary files produced during compilation.
# WARNING: if any generated file (e.g. TP1Parser.java) has been manually
# modified, it will nevertheless be removed by this rule. Use with caution.
clean:
	rm -f *.class $(G)Parser.java $(G)Lexer.java $(G)*.tokens

# A special Makefile syntax to indicate that these rules must always be
# executed when invoked even if their files are "up-to-date".
.PHONY: run clean

