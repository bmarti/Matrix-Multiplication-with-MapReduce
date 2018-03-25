#liste des fichiers

LISTE=`ls *.txt`

#compilation du fichier Stat.java

javac Stat.java

##   supression des anciens fichiers
rm dStat/Moyenne.txt
rm dStat/Variance.txt
rm dStat/Ecart_type.txt
rm dStat/MoyenneTri.txt
rm dStat/VarianceTri.txt
rm dStat/Ecart_typeTri.txt

if [ ! -d dStat ];then
	mkdir dStat
fi

for i in $LISTE
	do
		java Stat $i 10
	done

mv Moyenne.txt dStat
mv Variance.txt dStat
mv Ecart_type.txt dStat		

sort -nk 1 dStat/Moyenne.txt >> dStat/MoyenneTri.txt
sort -nk 1 dStat/Variance.txt >> dStat/VarianceTri.txt
sort -nk 1 dStat/Ecart_type.txt >> dStat/Ecart_typeTri.txt	

