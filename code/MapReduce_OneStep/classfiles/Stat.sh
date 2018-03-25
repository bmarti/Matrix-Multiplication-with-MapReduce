#liste des fichiers

LISTE=`ls *.txt`

#compilation du fichier Stat.java

javac Stat.java

##   supression des anciens fichiers
rm  Moyenne.txt
rm Variance.txt
rm Ecart_type.txt

mkdir dStat

for i in $LISTE
	do
		java Stat $i 10
	done

mv Moyenne.txt dStat
mv Variance.txt dStat
mv Ecart_type.txt dStat		

	

