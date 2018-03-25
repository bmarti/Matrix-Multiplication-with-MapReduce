------------------------------------------------------
     Manuel d'utilisation de launcher/sh en local
------------------------------------------------------


Pour le bon fonctionnement du produit de matrice MapReduce One Step non sécurisé:

Le produit de matrice effectué est une matrice M par une matrice N: M*N.

1 - Rassembler les 3 fichiers java (Map.java, Reduce.java et MatrixMultiply.java) et le script de lancement (launcher.sh) dans un même répertoire.
2 - Lancer le script à l'aide de la commande: bash launcher.sh <@param: dimension>
L'argument dimension étant un entier positif correspondant aux nombres de lignes et de colonnes des deux matrices.
3 - Une fois le job terminé, vous pouvez afficher le fichier contenant le résultat avec la commande: cat classfiles/resources/part-r-00000

Pour toute question, contactez-nous par mail:
baptiste.martinez@etu.uca.fr
rodrigue.abbe@etu.uca.fr
