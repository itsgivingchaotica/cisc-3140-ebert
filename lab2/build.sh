#!/bin/zsh

JAVA_PROGRAMS="Lab2DueDates.java"
TXT_FILE="lab2data.txt"
EXEC="Lab2DueDates"

build_class()
{
    INPUT=$*

    for JAVA_FILE in $INPUT
    do
	CLASS=${JAVA_FILE/%.java/.class}
	CLASSES="$CLASS $CLASSES"
	if [[ $JAVA_FILE -nt $CLASS ]]
	then
	echo "Compiling $JAVA_FILE..."
	javac -cp $CLASS $JAVA_FILE 
	fi
    done
}

build_exec()
{
    JAVA_CLASSES=$*
    for CLASS in $JAVA_CLASSES
    do
	echo "Building $EXEC..."
	java $EXEC $JAVA_CLASSES < $TXT_FILE
	break
    done
}

compile_run() {
    build_class $JAVA_PROGRAMS
    build_exec $CLASSES 
 }
    

