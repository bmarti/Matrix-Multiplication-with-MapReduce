# Matrix-Multiplication-with-MapReduce-Java
Implémentation d'algorithmes respectueux de la vie privée et comparaison de complexité des temps de calculs de plusieurs méthodes.

Les Readme sont situés dans le code du One Step. Les textes ont également été reportés ci-dessous.

Le code comporte essentiellement des lignes Java, du C et du bash permettent le lancement des calculs et l'écriture des résultats des calculs.

Un script a été implémenté pour installer from scratch une machine virtuelle avec Hadoop.

-------------------------------------------------------------------------------------
### Pour le bon fonctionnement du produit de matrice MapReduce One Step non sécurisé:
-------------------------------------------------------------------------------------

Le produit de matrice est effectué entre une matrice de dimension M et une matrice de dimension N : M*N.
M et N deux entiers strictement positifs.

Suivre ces étapes <u>avant</u> d'exécuter le script du job MapReduce.

1. Rassembler les 3 fichiers java (Map.java, Reduce.java et MatrixMultiply.java) et le script de lancement (launcher.sh) dans un même répertoire.
2. Créer un dossier nommé classfiles dans ce répertoire.
3. Compiler le fichier CreaRandMat.c afin d'avoir en main l'exécutable délivrant les matrices aléatoires.
N.B: On notera CreaRandMat l'exécutable associé.
4. Créer les deux matrices aléatoirement en lançant CreaRandMat.
	<br>4.1. Entrer le nombre de lignes voulues.
	<br>4.2. Entrer le nombre de colonnes voulues.
	<br>4.3. Choisir le label de la matrice (M ou N) pour remplir la première valeur dans les clés du fichier, noter que cela sera également le nom du fichier en sortie.

5. Placer le script clusterLauncher.sh dans le même répertoire que les fichiers .java
6. Lancer le job en tapant : bash clusterLauncher.sh
7. A la fin du job, les résultats sont contenus dans le dossier du HDFS par le chemin : hdfs://10.0.1.28:9000/result/, le nom du fichier en sortie commence par "part-r-" et est suivi du numéro de fichier sans espace, en général 00000.

Ligne de commande exacte (au numéro du fichier de sortie près) pour accéder au résultat:
> <font color='gray'>*sudo -u hadoop /opt/hadoop/bin/hdfs dfs -cat hdfs://10.0.1.28:9000/result/part-r-00000*</font>

<u>N.B:</u> Bien vérifier à ce que les tailles des matrices permettent un produit correct.
Multiplier une matrice 10*2 par une matrice 3*9 conduira à une erreur lors de l'exécution du job MapReduce.

------------------------------------------------------------------------
# How to install Hadoop and run job matrix multiplication with MapReduce
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
