# creation of the folder dedicated to the job
cd ~

if [ ! -d jobMRTS ];then
    mkdir jobMRTS
fi
cd jobMRTS

if [ ! -d classfiles ];then
    mkdir classfiles/
    mkdir classfiles/resources/
fi

# Placer les fichiers des matrices M et N dans le dossier classfiles/resources/ avant de lancer
#echo "Le calcul va être lancé..."

echo "Nettoyage d'anciens fichiers..."
cd classfiles/
rm -f MatrixMultiplyTwoSteps.class
rm -f MapTwoSteps.class
rm -f ReduceTwoSteps.class
rm -f MapTwoSteps2.class
rm -f ReduceTwoSteps2.class
rm -rf result
#secure-mapreduce-f4/code/MapReduce_TwoSteps
#Retour dans le dossier ~/jobMRTS
cd ..

cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/crmfs.c .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/Paillier.java .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/MatrixMultiplyTwoSteps.java .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/MapTwoSteps.java .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/MapTwoSteps2.java .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/ReduceTwoSteps.java .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/ReduceTwoSteps2.java .
cp ~/secure-mapreduce-f4/code/MapReduce_TwoSteps/secured/launcher.sh .

gcc -o crmfs crmfs.c

## Creation  & Deplacement de deux matrices M et N en prenant en compte la taille entrée en argument au lancement du script
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

echo "Compilation des fichiers nécessaires au programme..."
## Compilation du Mapper (MapTwoSteps.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. MapTwoSteps.java
## Compilation du Reducer (ReduceTwoSteps.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. ReduceTwoSteps.java
## Compilation du Mapper (MapTwoSteps2.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. MapTwoSteps2.java
## Compilation du Reducer (ReduceTwoSteps2.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. ReduceTwoSteps2.java
## Compilation du programme principal (MatrixMultiplyTwoSteps.java):
javac -cp hadoop-common-3.0.0.jar:hadoop-mapreduce-client-core-3.0.0.jar:$(hadoop classpath):. MatrixMultiplyTwoSteps.java
echo "Compilation complète !"

echo "Création de l'archive jar"
## Création de l'archive jar:
mv MatrixMultiplyTwoSteps.class classfiles/
mv MapTwoSteps.class classfiles/
mv ReduceTwoSteps.class classfiles/
mv MapTwoSteps2.class classfiles/
mv ReduceTwoSteps2.class classfiles/
mv Paillier.class classfiles/

cd classfiles/
jar -cvf MatrixMultiplyTwoSteps.jar *.class

echo "Archivage terminé"

echo "==== Début du job MapReduce ===="
## Lancement du job MapReduce:
hadoop jar MatrixMultiplyTwoSteps.jar MatrixMultiplyTwoSteps resources/ result/ $1
echo "==== Job terminé ! ===="
