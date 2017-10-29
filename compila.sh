#!/bin/bash
echo "STARTING..."
java -cp antlr.jar antlr.Tool gramatica.g
echo "PARSER AND LEXER GENERATED"
if [ $? -eq 0 ]; then
   javac -classpath antlr.jar:. *.java
   echo "ALL CLASSES COMPILED"
   if [ $? -eq 0 ]; then
       java -cp antlr.jar:. MainClass < input.in | tee Compilado.java
       javac Compilado.java
       if [ $? -eq 0 ]; then
         echo "-- Compilado.java COMPILED!"
       else
         echo "Compilado.java COULDN't COMPILE..."
       fi
   else 
       echo "PROBLEM COMPILING"
   fi
else
   echo "PROBLEM GENERATING PARSER"
fi