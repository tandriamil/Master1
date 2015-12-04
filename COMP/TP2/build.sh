#!/bin/sh

# Run script of the duo ANDRIAMILANTO Tompoariniaina and IHSINE Azzeddine

# Some local definitions
src_dir=./src
bin_dir=./bin
test_dir=./tests

# Some local definitions
if [ $LOCAL_ANTLR != "" ]
then
	antlr_jar=$LOCAL_ANTLR
else
	antlr_jar=/share/m1info/COMP/antlr/antlr-3.5.jar
fi

# Run tests and build it
java -cp $bin_dir:$antlr_jar VslComp $test_dir/personnal_tests/my_test.vsl > build