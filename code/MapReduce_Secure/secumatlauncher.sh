#!/bin/bash

javac -cp /usr/local/hadoop/share/hadoop/common/hadoop-common-3.0.0.jar:/usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-3.0.0.jar:/usr/local/hadoop/bin/hadoop:. SecuMat.java

jar -cvf SecuMat.jar SecuMat.class

hadoop jar SecuMat.jar
