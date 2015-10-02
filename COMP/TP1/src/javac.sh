#!/bin/sh

# javac.sh: compiles all .java files in the current directory
# exemple: ./javac.sh

# Tests if the ANTLR file is readable
ANTLR_JAR=/share/m1info/COMP/antlr/antlr-3.5.jar
if [ ! -r $ANTLR_JAR ]
then
  echo "error: could not read ANTLR Jar file: $ANTLR_JAR"
  echo "check if file exists and is readable"
  exit 2
fi

export CLASSPATH=/share/m1info/COMP/antlr/antlr-3.5.jar:.:$CLASSPATH
javac *.java

