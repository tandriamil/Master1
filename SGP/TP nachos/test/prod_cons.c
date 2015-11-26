#include "userlib/syscall.h"
#include "userlib/libnachos.h"

#define MAX 10
int nb = 0, buffer[10], cpt1 = 0, cpt2 = 0; //un nombre qui sera la ressource commune

//Déclaration de sémaphores
LockId multex; //EM entre producteurs ou consommateurs // a
SemId full; //attente de producteur par le consommateur // b
SemId plein;//attente des producteurs en cas de buffer plein // c
void prod (void *d)
{
  while(1){
		 P(plein);
		 LockAcquire(multex);
		 n_printf("Je suis le producteur et je modifie la ressource commune\n" );
		 nb++;
		 n_printf("Je suis le producteur et je met la ressource commune dans le buffer\n" );
		 buffer[cpt1] = nb;
		 cpt1++;
		 if (cpt1 == MAX)
		 {
		    cpt1 = 0;
		 }
		 LockRelease(multex);
		 V(full);
   }
}
void cons (void *d)
{
   while(1){
		 P(full);
		 LockAcquire(multex);
		 n_printf ("Je suis le consommateur et je prend la ressource commune dans le buffer\n" );
		 nb = buffer[cpt2];
		 n_printf ("Je suis le consommateur et j'affiche le nombre qui est: %d\n",nb);
		 cpt2++;
		 if (cpt2 == MAX)
		 {
		    cpt2 = 0;
		 }
		 LockRelease(multex);
		 V(plein);
   }
}

int main (void)
{
		 ThreadId produ;
		 ThreadId conso;
		 int nbprod = 5, nbconso = 5, t;
		 //initialisation des semaphores
	 	 multex = LockCreate("multex");
		 full = SemCreate("full",0); 
		 plein = SemCreate("plein",0);
	
 		 for (t = 0; t < 10; t++)
		 {
		    buffer[t] = 0;
		 }
	
		 for (t = 0; t < nbprod; t++)
		 { 
		    produ = newThread("Producteur", (int)prod, 0);
		    if (produ)
		    {
		       n_printf ("ERROR; return code from pthread_join() is %d\n", produ);
		    }
		 }
		 for (t = 0; t < nbconso; t++)
		 {
		    conso = newThread("Consomateur", (int)cons, 0);
		    if (conso)
		    {
		       n_printf ("ERROR; return code from pthread_join() is %d\n", conso);
		    }
		 }
		 Join(produ);
		 Join(conso);
   return 0;
}
