#!/bin/bash
javac src/main/java/jppf/GenericResult.java
cd src/main/java
jar -cvf ../../../GenericResult.jar jppf/GenericResult.class
rm jppf/GenericResult.class