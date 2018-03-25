#!/bin/bash

# creation of the folder dedicated to the job
CHEMINC=`pwd`
cd ~

if [ ! -d jobMR ];then
    mkdir jobMR
fi

cd jobMR

if [ ! -d classfiles ];then
    mkdir classfiles/
fi

if [ ! -d classfiles/resources ];then
    mkdir classfiles/resources/
fi

## Placer les fichiers des matrices M et N dans le dossier classfiles/resources/ avant de lancer
#echo "Le calcul va être lancé..."

echo "Nettoyage d'anciens fichiers..."
cd classfiles/
rm -f MatrixMultiply.class
rm -f Map.class
rm -f Reduce.class

#Retour dans le dossier ~/jobMR
cd ..

cp "$CHEMINC/crmfs.c" .
cp "$CHEMINC/MatrixMultiply.java" .
cp "$CHEMINC/Map.java" .
cp "$CHEMINC/Reduce.java" .
cp "$CHEMINC/launcher.sh" .

gcc -o crmfs crmfs.c

echo "Compilation des fichiers nécessaires au programme..."
## Compilation du Mapper (Map.java):
javac -cp hadoop-common-2.8.2.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. Map.java
## Compilation du Reducer (Reduce.java):
javac -cp hadoop-common-2.8.2.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. Reduce.java
## Compilation du programme principal (MatrixMultiply.java):
javac -cp hadoop-common-2.8.2.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. MatrixMultiply.java
echo "Compilation complète !"

echo "Création de l'archive jar"
## Création de l'archive jar:
mv MatrixMultiply.class classfiles/
mv Map.class classfiles/
mv Reduce.class classfiles/
cd classfiles/
jar -cvf MatrixMultiply.jar *.class
echo "Archivage terminé"

## Creation  & Deplacement de deux matrices M et N en prenant en compte la taille entrée en argument au lancement du script

for i in {1..10}
do
    cd ~/jobMR
    rm -rf classfiles/result

    ./crmfs $1 M
    
    echo "Déplacement de M dans le dossier resources"
    if [ -f "./M" ];then
	mv M classfiles/resources/
    fi 
    sleep 0.05
    ./crmfs $1 N

    echo "Déplacement de N dans le dossier resources"
    if [ -f "./N" ];then
	mv N classfiles/resources/
    fi
    cd classfiles
    
    echo "==== Début du job MapReduce ===="
    ## Lancement du job MapReduce:
    EXECTIME=$(hadoop jar MatrixMultiply.jar MatrixMultiply resources/ result/ $1)
    FILENAM="$1.txt"
    echo -n "$EXECTIME " >> $FILENAM
done
echo "==== Les 10 jobs sont terminés ===="
