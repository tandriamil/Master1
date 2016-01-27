#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <time.h>


// Constants
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE 10


/**
 * Generate a random double
 * Return: A random double
 */
int int_random() {
	return (int)(rand() % (RAND_MAX_VALUE-RAND_MIN_VALUE) + RAND_MIN_VALUE);
}


int main(int argc,char *argv[])
{
	
	int n, i, j, k;
	
	if (argc > 2)
   {
      fprintf(stderr,"%s: invalid number of parameters \n",argv[0]);
      return 1;
   }
	
   else if (argc < 2)
   {
      fprintf(stdout,"%s: please provide the size of the matrices : ",argv[0]);
      fscanf(stdin,"%d",&n);
   }
   else
   {
      n = atoi(argv[1]);
   }
   
   if ((n < 0) || (n >64))
   {
      fprintf(stderr,"%s: invalid value for n, must be greater than 0 and lesser than 64!\n",argv[0]);
      return 1;
   }
   
   srand(time(NULL));
   
   // The matrices
	int m1[n][n], m2[n][n], result[n][n];
	
	// Initialize the two matrices
	for (i = 0; i < n; ++i) {
		for (j = 0; j < n ; ++j) {
			m2[i][j] = int_random();
			m1[i][j] = int_random();
			result[i][j] = 0;
		}
	}
	
	// Display the matrices
	fprintf(stderr, "\nFirst matrice:\n");
	for (i = 0; i < n; ++i) {
		for (j = 0; j < n; ++j) {
			fprintf(stderr, "[%d] ", m1[i][j]);
		}
		fprintf(stderr, "\n");
	}
	fprintf(stderr, "\nSecond matrice:\n");
	for (i = 0; i < n; ++i) {
		for (j = 0; j < n; ++j) {
			fprintf(stderr, "[%d] ", m2[i][j]);
		}
		fprintf(stderr, "\n");
	}
   
  #pragma omp parallel private(i, j, k) shared(n, m1, m2,result)
   {
	   
	   #pragma omp for schedule(dynamic)
		for (i = 0; i < n; ++i) {
			for (j = 0; j < n; ++j) {
				for (k = 0; k < n; ++k) {
					result[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
	}
	
	// Display the result
	fprintf(stderr, "\nResult matrice:\n");
	for (i = 0; i < n; ++i) {
		for (j = 0; j < n; ++j) {
			fprintf(stderr, "[%d] ", result[i][j]);
		}
		fprintf(stderr, "\n");
	}
	
	return 0;
};
