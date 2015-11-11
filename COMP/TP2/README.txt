- La stratégie de travail :

	Nous avons partagé le code entre 2 fichiers :

	Le fichier VSLTreeParser.g dans lequel nous gérons les différentes instructions du langage VSL+, nous appelons les fonctions du fichier Code3aGenerator.java afin de produire du code 3 adresses que nous concaténons avec le reste du code de la grammaire VSL.

	Les différentes instructions ont (symTab) table de symboles comme paramètre dans lequel nous y enregistrons les variables, les fonctions, les prototypes.

	Le fichier Code3aGenerator.java dans lequel nous programmons les fonctions de génération de code 3 adresses que nous rappelons dans le fichier VSLTreeParser.g.

	Quant à l'ordre de programmation des instructions, nous avons suivi l'ordre proposé sur le sujet du TP, à savoir : les expressions simples, l'instruction d'affectation, la gestion des blocs, la déclaration des variables, les expressions avec les variables, les instructions de contrôle if, while, les fonctions PRINT et READ.

	Nous avons suivi le protocole sur le fait de faire des tests unitaires lors de la programmation de chaque instruction afin de toujours savoir où notre programme ne fonctionne pas et ne pas avoir une erreur sur laquelle nous avons aucune information (ce qui nous ferait perdre beaucoup de temps).

- État d'avancement : 

	Quant à l'état d'avancement, nous n'avons pas pu finir le codage des tableaux ainsi que les instructions ayant une relation avec les tableaux. Sinon, le reste des instructions a été programmé.

- Test : 

