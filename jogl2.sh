#!/bin/sh
CLASSPATH=.:gluegen-rt-natives-macosx-universal.jar:gluegen-rt.jar:jogl-all-natives-macosx-universal.jar:jogl-all.jar
export CLASSPATH
javac *.java
java CgMain
