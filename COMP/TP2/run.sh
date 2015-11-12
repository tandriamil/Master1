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
if [ -n $LOCAL_ANTLR ]
then
	antlr_jar=$LOCAL_ANTLR
else
	antlr_jar=/share/m1info/COMP/antlr/antlr-3.5.jar
fi

# Generate java files from grammars
#echo "Generating java files from grammars"
#java -cp $bin_dir:$antlr_jar org.antlr.Tool $src_dir/VSLTreeParser.g
#java -cp $bin_dir:$antlr_jar org.antlr.Tool $src_dir/VSLParser.g
#echo " [DONE]"

# Compile sources
#echo "\nCompile sources"
#javac -cp $bin_dir:$antlr_jar -d $bin_dir $src_dir/*.java
#echo " [DONE]"

# Run tests
echo "\nRunning tests\n"
echo "\nCode result for hello_world.vsl:"
java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/testlevel1/hello_world.vsl

echo "\nCode result for level1block.vsl:"
java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/testlevel1/level1block.vsl

echo "\nCode result for level1expr.vsl:"
java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/testlevel1/level1expr.vsl
