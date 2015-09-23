#!/bin/sh
./my_g2java.sh ../src/Expr.g
./my_javac.sh
./my_run.sh Test < simple_test
./my_run.sh Test < test_division
./my_run.sh Test < test_division_by_zero
./my_run.sh Test < test_puissance
./my_run.sh Test < test_puissance_bis
