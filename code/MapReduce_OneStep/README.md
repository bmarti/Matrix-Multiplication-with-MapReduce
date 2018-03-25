### Pour le bon fonctionnement du produit de matrice MapReduce One Step non sécurisé:

Le produit de matrice effectué est une matrice M par une matrice N: M*N.

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

Pour toute question, contactez-nous par mail:
baptiste.martinez@etu.uca.fr
rodrigue.abbe@etu.uca.fr