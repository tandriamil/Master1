// Fonctions auxiliaires
#include <stdio.h>

// Depuis le SDK Cuda
#define CUDA_SAFE_CALL_NO_SYNC(call) do {
	cudaError err = call;
	if (cudaSuccess != err) {
		fprintf(stderr, "Cuda error in file '%s' in line %i : %s.\n", __FILE__, __LINE__, cudaGetErrorString(err));
		exit(EXIT_FAILURE);
	}
} while(0);

#define CUDA_SAFE_CALL(call) CUDA_SAFE_CALL_NO_SYNC(call)

float random_float(int emin, int emax, int pos_neg);

double getclock();
