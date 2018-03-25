#!/bin/bash

clear
CHEMINC=`pwd`

echo "Updating repositories..."
sudo apt-get update -y

#sudo apt-get install git-all -y
#sudo apt-get install build-essential -y

echo "Uninstalling former version of Java to avoid conflicts"
sudo apt-get purge openjdk-\* -y
sudo apt-get autoremove -y
echo "Installing Java 8 by default"
sudo apt-get install default-jdk -y

echo "Extracting tar file..."

# On estime que pour obtenir ce script bash, l'utilisateur aura déjà installé Git et effectué un git clone du dépôt
# git clone https://forge.clermont-universite.fr/git/secure-mapreduce-f4

cd "$CHEMINC/.."
sudo tar xzvf hadoop-3.0.0.tar.gz
sudo mv hadoop-3.0.0 /usr/local/hadoop

# writing into bashrc file until EOF is read
cat >> ~/.bashrc <<EOF
export HADOOP_HOME=/usr/local/hadoop
export HADOOP_MAPRED_HOME=/usr/local/hadoop
export HADOOP_COMMON_HOME=/usr/local/hadoop
export HADOOP_HDFS_HOME=/usr/local/hadoop
export PATH=$PATH:.:/usr/local/hadoop/bin/
export JAVA_HOME=/usr/
EOF

# load modified bashrc
. ~/.bashrc
# source ~/.bashrc ne fonctionne pas pour une raison inconnue, cependant la commande du dessus fonctionne.

# creation of the folder dedicated to the job
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

if [ -f "MatrixMultiply.class" ];then
    rm -f MatrixMultiply.class
fi

if [ -f "Map.class" ];then
    rm -f Map.class
fi

if [ -f "Reduce.class" ];then
    rm -f Reduce.class
fi

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
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. Map.java
## Compilation du Reducer (Reduce.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. Reduce.java
## Compilation du programme principal (MatrixMultiply.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. MatrixMultiply.java
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
cd ~/jobMR
rm -rf classfiles/result

./crmfs $1 M

echo "Déplacement de M dans le dossier resources"
if [ -f "./M" ];then
    mv M classfiles/resources/
fi 

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
echo "==== Hadoop est installé et le job MapReduce est terminé ===="
