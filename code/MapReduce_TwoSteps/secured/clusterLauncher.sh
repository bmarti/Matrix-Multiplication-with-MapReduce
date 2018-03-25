#!/bin/bash

# Placer les fichiers des matrices M et N dans le dossier classfiles/resources/ avant de lancer
echo "Le calcul va être lancé..."
if [ ! -d "./classfiles" ];then
    mkdir classfiles
fi
echo "Nettoyage d'anciens fichiers..."
if [ -d "./classfiles" ];then 
    cd classfiles/
    rm -f *.class
    cd ..
fi
if [ "sudo -u hadoop /opt/hadoop/bin/hdfs dfs -test -d hdfs://10.0.1.43:9000/resources/" ];then
    sudo -u hadoop /opt/hadoop/bin/hdfs dfs -mkdir hdfs://10.0.1.43:9000/resources/
fi
echo "Compilation des fichiers nécessaires au programme..."
# Compilation du Mapper (MapTwoSteps.java):
javac -cp /opt/hadoop-2.7.1/share/hadoop/common/hadoop-common-2.7.1.jar:/opt/hadoop-2.7.1/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar:$(hadoop classpath):. MapTwoSteps.java
# Compilation du Reducer (ReduceTwoSteps.java):
javac -cp /opt/hadoop-2.7.1/share/hadoop/common/hadoop-common-2.7.1.jar:/opt/hadoop-2.7.1/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar:$(hadoop classpath):. ReduceTwoSteps.java
# Compilation du Mapper (MapTwoSteps2.java):
javac -cp /opt/hadoop-2.7.1/share/hadoop/common/hadoop-common-2.7.1.jar:/opt/hadoop-2.7.1/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar:$(hadoop classpath):. MapTwoSteps2.java
# Compilation du Reducer (ReduceTwoSteps2.java):
javac -cp /opt/hadoop-2.7.1/share/hadoop/common/hadoop-common-2.7.1.jar:/opt/hadoop-2.7.1/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar:$(hadoop classpath):. ReduceTwoSteps2.java
# Compilation du programme principal (MatrixMultiplyTwoSteps.java):
javac -cp /opt/hadoop-2.7.1/share/hadoop/common/hadoop-common-2.7.1.jar:/opt/hadoop-2.7.1/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar:$(hadoop classpath):. MatrixMultiplyTwoSteps.java
# Compilation du programme lancant le chiffrement de Paillier (Paillier.java):
javac Paillier.java

echo "Compilation complète !"

gcc -o crmfs crmfs.c

echo "Création de l'archive jar"
# Création de l'archive jar:
mv *.class classfiles/
cd classfiles/
jar -cvf MatrixMultiplyTwoSteps.jar *.class

echo "Archivage terminé"

for i in {1..10}
do
    sudo -u hadoop /opt/hadoop/bin/hdfs dfs -rm -R hdfs://10.0.1.43:9000/result  
    ../crmfs $1 M
    echo "Déplacement de M dans le dossier resources"
    sudo -u hadoop /opt/hadoop/bin/hdfs dfs -put -f M hdfs://10.0.1.43:9000/resources/

    # Création de la matrice N & Déplacement dans le dossier resources
    ../crmfs $1 N
    echo "Déplacement de N dans le dossier resources"
    sudo -u hadoop /opt/hadoop/bin/hdfs dfs -put -f N hdfs://10.0.1.43:9000/resources/
    
    echo "==== Début du job MapReduce ===="
    ## Lancement du job MapReduce:
    EXECTIME=$(hadoop jar MatrixMultiplyTwoSteps.jar MatrixMultiplyTwoSteps hdfs://10.0.1.43:9000/resources/ hdfs://10.0.1.43:9000/result/ $1)
    FILENAM="$1.txt"
    echo -n "$EXECTIME " >> $FILENAM
done
echo "==== Les 10 jobs sont terminés ===="
