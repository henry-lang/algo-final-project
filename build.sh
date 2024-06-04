#!/bin/sh

# Create the build directory if it doesn't exist
mkdir -p build

# Compile Java files to the build directory
javac -d build *.java &&
java -cp build FinalProject