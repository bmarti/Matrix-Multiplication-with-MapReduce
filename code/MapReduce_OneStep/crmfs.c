#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include "time.h"

/* Algorithme créant aléatoirement une matrice M ou N composée de valeurs aléatoires 
 * comprises entre 0 et 9 dans le fichier MatriceCree.
 * Il est laissé au choix de l'utilisateur du nombre de lignes et de colonnes.
 * Entrée: aucune
 * Sortie: fichier contenant la matrice, nommée par le label rentré par l'utilisateur
 */


int main(int argc, char ** argv)
{
	FILE * fichier;
	int nb_lig=5;
	int nb_col=5;
        int A_ij=0;
	int i,j;
	char label[2];
	nb_lig=atoi(argv[1]);
	nb_col=atoi(argv[1]);
	strcpy(label,argv[2]);
	(void) argc;
	fichier=fopen(label,"w");
	srand(time(NULL));
	if(fichier)
	{
		
		for(i=0;i<nb_lig;i++)
		{
			for(j=0;j<nb_col;j++)
			{
				A_ij = 1*rand()%10;
				fprintf(fichier,"%s,%d,%d,%d\n",label,i,j,A_ij);
			}
		}
		fclose(fichier);
	}
	else
	{
		printf("pb ouverture fichier");
	}
	
	return 0;
	
}
