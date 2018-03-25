------------------------------------------------------------------------
How to install Hadoop and run job matrix multiplication with MapReduce
------------------------------------------------------------------------

Prerequisites:
- the "default-jdk" command line should support JDK 8 or later, it depends on your Linux version.
- do a git clone of the repository in your PC.
- hadoopFromScratch.sh must be located in the same folder as .java files.

How to run "hadoopFromScratch.sh":

	bash hadoopFromScratch.sh <@param: matrix dimension>


!!! Don't forget the parameter matrix dimension which enables to create
	matrix for the MapReduce job or you will have a segmentation fault error.
	
N.B: Matrix dimension should be a positive integer (i.e. strictly above 0).

File included: 	crmfs.c 
				Map.java
				Reduce.java
				MatrixMultiplication.java

Description:
We install Java 8
We install Hadoop 3.00
We create two random matrix called M and N 
We run job MapReduce for matrix multiplication


To access the result of matrix multiplication
<font color='gray'>cat /result/part-r-00000</font>

For any question, contact us:
baptiste.martinez@etu.uca.fr
rodrigue.abbe@etu.uca.fr
