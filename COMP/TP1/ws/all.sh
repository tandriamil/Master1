#!/bin/sh

# Create the java files
#./my_g2java.sh ../src/Partie1.g
./my_g2java.sh ../src/Partie2.g

# Compile them
./my_javac.sh

# Execute tests on them
#echo "Test of part 1 with test1"
#./my_run.sh Test1 < ../tests/test1.ttl

#echo "\nTest of part 1 with test2"
#./my_run.sh Test1 < ../tests/test2.ttl

echo "\nTest of part 2 with test1"
./my_run.sh Test2 < ../tests/test1.ttl

echo "\nTest of part 2 with test2"
./my_run.sh Test2 < ../tests/test2.ttl
