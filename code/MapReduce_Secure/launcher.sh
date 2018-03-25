#!/bin/bash
CHEMINC=`pwd`

if [ ! -d input ];then
    mkdir input
fi

if [ -f MatrixMultiply.jar ];then
    rm -r MatrixMultiply.jar
fi

gcc -o crmfs crmfs.c

echo "Compilation des fichiers nécessaires au programme..."
# Compilation de l'algorithme de chiffrement (Paillier.java):
javac Paillier.java
# Compilation du Mapper (Map.java):
javac -cp /usr/local/hadoop/share/hadoop/common/hadoop-common-3.0.0.jar:/usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-3.0.0.jar:/usr/local/hadoop/bin/hadoop:. Map.java
# Compilation du Reducer (Reduce.java):
javac -cp /usr/local/hadoop/share/hadoop/common/hadoop-common-3.0.0.jar:/usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-3.0.0.jar:/usr/local/hadoop/bin/hadoop:. Reduce.java
# Compilation du programme principal (MatrixMultiply.java):
javac -cp /usr/local/hadoop/share/hadoop/common/hadoop-common-3.0.0.jar:/usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-3.0.0.jar:/usr/local/hadoop/bin/hadoop:. MatrixMultiply.java
echo "Compilation complète !"

jar -cvf MatrixMultiply.jar MatrixMultiply.class Reduce.class Map.class Paillier.class

if [ ! -d ~/jobMRSecu ];then
    mkdir ~/jobMRSecu
fi

# Creation de deux matrices M et N en prenant en compte la taille entrée en argument au lancement du script

for i in {1..10}
do
    cd $CHEMINC
    rm -rf result
    
    ./crmfs $1 M
    if [ -f "./M" ];then
	mv ./M input/
    fi
    
    ./crmfs $1 N
    if [ -f "./N" ];then
	mv ./N input/
    fi
    
    echo "==== Début du job MapReduce ===="
    # Lancement du job MapReduce:
    EXECTIME=$(hadoop jar MatrixMultiply.jar MatrixMultiply input/ result/ $1)
    FILENAM="$1secu.txt"
    echo -n "$EXECTIME " >> "~/jobMRSecu/$FILENAM"
done
echo "==== Les 10 jobs sont terminés ===="

#hadoop jar MatrixMultiply.jar MatrixMultiply input/ result/ $1
#echo "==== Job terminé ! ===="
