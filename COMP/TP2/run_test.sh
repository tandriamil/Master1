#!/bin/bash

# Some local definitions
bin_dir=./bin
test_dir=./tests

# Some local definitions
if [ $LOCAL_ANTLR != "" ]
then
	antlr_jar=$LOCAL_ANTLR
else
	antlr_jar=/share/m1info/COMP/antlr/antlr-3.5.jar
fi

# Runs tests
echo "Running tests"
echo "My own test file"
java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/personnal_tests/my_test.vsl
echo ""

# The example test provided
# echo ""
# echo "The example test provided"
# java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/personnal_tests/example_test.vsl
# echo ""

# echo "Code result for hello_world.vsl:"
# java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/testlevel1/hello_world.vsl
# echo ""

# echo "Code result for level1block.vsl:"
# java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/testlevel1/level1block.vsl
# echo ""

# echo "Code result for level1expr.vsl:"
# java -cp $src_dir:$bin_dir:$antlr_jar VslComp $test_dir/testlevel1/level1expr.vsl
# echo ""
