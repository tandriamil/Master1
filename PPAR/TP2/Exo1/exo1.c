#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int main(int argc,char *argv[])
{
	
	int nb, me;
   
   #pragma omp parallel private(nb,me)
   {
	   nb = omp_get_num_threads();
	   me = omp_get_thread_num();
	   
	   printf("Hello world, thread n°%d\n",me);
	   
	   #pragma omp master
	   {
		   printf("There is %d threads printing Hello world\n",nb);
	   }
	}
	return 0;
};
