# SkeLaTeX
Squelette LaTeX pour rapport de projet et de stage ISIMA

La majoriter des informations sont configurables dans le fichier `input/config.tex` et permet de changer facilement le nom, logo et adresse de l'école et de l'entreprise. Permet d'indiquer le type de rapport dont il s'agit, l'année et la fillière.

## Organisation

* `code` contient des extraits de codes pour le rapport.

* `glossaire` contient la liste des définitions présentes dans le glossaire, les fichiers doivent respecter la nomenclature suivante :
	~~~
		Nom du terme à définir
		La définition avec du code \LaTeX.
	~~~

* `img` contient les images présentes dans le rapport.

* `input` contient la bibliographie, la configuration, le résumé, les remerciements et un fichier contenant toutes les inclusions de bibliothèques (`preambule.tex`).

* `logo` contient les logo pour la page de garde.

* `model` contient des scripts pour la mise en forme et le model pour la page de garde.

* `part` contient les différentes parties du rapport (donc la majorité du rapport).

