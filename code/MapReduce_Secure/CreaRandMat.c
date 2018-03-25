#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include "time.h"

/*CreaRandMat.c permet de créer une matrice aléatoire , adaptée au 
 * mapreduce, dans le fichier MatriceCree
 * Entree: rien
 * Sortie: MatriceCree
 * */

int main(int argc, char ** argv)
{
	FILE * fichier=fopen("MatriceCree","w");
	
	int nb_lig;
	int nb_col;
	int A_ij=0;
	char label[0];
	int i,j;
	printf("nb de lignes: ");
	scanf("%d",&nb_lig);
	printf("\nnb de colonnes: ");
	scanf("%d:",&nb_col);
	printf("\nlabel de la matrice :M, N? ");
	scanf("%s",label);
	if(fichier)
	{
		srand(time(NULL));
		for(i=0;i<nb_lig;i++)
		{
			for(j=0;j<nb_col;j++)
			{
				A_ij = rand()%10;
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
