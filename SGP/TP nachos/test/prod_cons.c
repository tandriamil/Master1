#include "userlib/syscall.h"
#include "userlib/libnachos.h"

#define MAX 10
#define NB_TOUR 5
int nb = 0, buffer[10], cpt1 = 0, cpt2 = 0; //un nombre qui sera la ressource commune

//Déclaration de sémaphores
SemId multex; //EM entre producteurs ou consommateurs 
SemId full; //attente de producteur par le consommateur 
SemId plein;//attente des producteurs en cas de buffer plein 
void prod (void *d)
{
	int nb_tour = 0;
  while(nb_tour < NB_TOUR){
		 P(plein);
		 P(multex);
		 n_printf("Je suis le producteur et je modifie la ressource commune\n" );
		 nb++;
		 n_printf("Je suis le producteur et je met la ressource commune dans le buffer\n" );
		 buffer[cpt1] = nb;
		 cpt1++;
		 if (cpt1 == MAX)
		 {
		    cpt1 = 0;
		 }
		 V(multex);
		 V(full);
		 nb_tour++;
   }

   Exit(0);
}
void cons (void *d)
{
	int nb_tour = 0;
  while(nb_tour < NB_TOUR){
		 P(full);
		 P(multex);
		 n_printf ("Je suis le consommateur et je prend la ressource commune dans le buffer\n" );
		 
		 nb = buffer[cpt2];
		 n_printf ("Je suis le consommateur et j'affiche le nombre qui est: %d\n",nb);	
		 cpt2++;
		 if (cpt2 == MAX)
		 {
		    cpt2 = 0;
		 }
		 V(multex);
		 V(plein);
		 nb_tour++;
   }

   Exit(0);
}

int main (void)
{
		 ThreadId produ;
		 ThreadId conso;
		 int nbprod = 2, nbconso = 2, t;
		 //initialisation des semaphores
	 	 multex = SemCreate("multex",1);
		 full = SemCreate("full", 1);
		 plein = SemCreate("plein", 1);
	
 		 for (t = 0; t < 10; t++)
		 {
		    buffer[t] = 0;
		 }
	
		 for (t = 0; t < nbprod; t++)
		 { 
		    produ = newThread("Producteur", (int)prod, 0);
		   /*if (produ)
		    {
		       n_printf ("ERROR; return code from pthread_join() is %d\n", produ);
		    }*/
		 }
		 for (t = 0; t < nbconso; t++)
		 {
		    conso = newThread("Consomateur", (int)cons, 0);
		    /*if (conso)
		    {
		       n_printf ("ERROR; return code from pthread_join() is %d\n", conso);
		    }*/
		 }

		 //n_printf("Before Joins");
		 Write("Before Joins", 12, ConsoleOutput);
		 Join(produ);
		 Join(conso);
   return 0;
}
