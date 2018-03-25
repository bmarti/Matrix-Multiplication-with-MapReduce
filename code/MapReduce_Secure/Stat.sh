#liste des fichiers

LISTE=`ls *.txt`

#compilation du fichier Stat.java

javac Stat.java

mkdir dStat

for i in $LISTE
	do
		java Stat $i 4
	done

mv Moyenne.txt dStat
mv Variance.txt dStat
mv Ecart_type.txt dStat		

	
 
