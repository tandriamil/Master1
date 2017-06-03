#!/bin/sh

# Run script of the duo ANDRIAMILANTO Tompoariniaina and IHSINE Azzeddine

# Some local definitions
src_dir=./src
bin_dir=./bin
test_dir=./tests

# If bin dir doesn't exist
if [ ! -d $bin_dir ]
then
	mkdir $bin_dir
fi

# Some local definitions
if [ $LOCAL_ANTLR != "" ]
then
	antlr_jar=$LOCAL_ANTLR
else
	antlr_jar=/share/m1info/COMP/antlr/antlr-3.5.jar
fi

# Generate java files from grammars
echo "Generating java files from grammars"
java -cp $bin_dir:$antlr_jar org.antlr.Tool $src_dir/VSLLexer.g
java -cp $bin_dir:$antlr_jar org.antlr.Tool $src_dir/VSLTreeParser.g
java -cp $bin_dir:$antlr_jar org.antlr.Tool $src_dir/VSLParser.g
echo " [DONE]"
echo ""

# Compile sources
echo "Compile sources"
javac -cp $bin_dir:$antlr_jar -d $bin_dir $src_dir/*.java
echo " [DONE]"
echo ""

# Run tests
./run_test.sh
