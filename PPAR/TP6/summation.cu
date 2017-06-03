#include "utils.h"
#include <stdlib.h>

struct results {
	float sum;
};

#include "summation_kernel.cu"

// CPU implementation
float log2_series(int n) {
	int i;
	float res = 0;
	for (i = n - 1; i >= 0; --i)
	{
		if(i%2==0) res += 1.0/(i+1.0);
		else res -= 1.0/(i+1.0);
	}
	return res;
}


int main(int argc, char ** argv) {
	int data_size = 1024 * 1024 * 128;

	// Run CPU version
	double start_time = getclock();
	float log2 = log2_series(data_size);
	double end_time = getclock();

	printf("CPU result: %f\n", log2);
	printf(" log(2)=%f\n", log(2.0));
	printf(" time=%fs\n", end_time - start_time);

	// Parameter definition
	int threads_per_block = 4 * 32;
	int blocks_in_grid = 8;

	int num_threads = threads_per_block * blocks_in_grid;

	// Timer initialization
	cudaEvent_t start, stop;
	CUDA_SAFE_CALL(cudaEventCreate(&start));
	CUDA_SAFE_CALL(cudaEventCreate(&stop));

	int results_size = num_threads;


	// As the input is just an int, we don't have to allocate GPU memory for it
	// We'll just pass it as parameter

	// Allocating output data on CPU
	float data_out_cpu[results_size];

	// Allocating output data on GPU
	float * data_out_gpu;
	cudaMalloc((void **)&data_out_gpu, results_size*sizeof(float));

	// Start timer
	CUDA_SAFE_CALL(cudaEventRecord(start, 0));

	// Launch kernel
	summation_kernel<<<blocks_in_grid, threads_per_block>>>(data_size, data_out_gpu);

	// Stop timer
	CUDA_SAFE_CALL(cudaEventRecord(stop, 0));
	CUDA_SAFE_CALL(cudaEventSynchronize(stop));

	// Get results back
	cudaMemcpy(data_out_cpu, data_out_gpu, results_size*sizeof(float), cudaMemcpyDeviceToHost);


	// Finish reduction
	float sum = 0.;
	int i, units_per_thread = data_size / num_threads;
	for (i = 0; i < results_size; ++i) {
		if((i*units_per_thread)%2==0) res += data_out_cpu[i];
		else res -= data_out_cpu[i];
	}

	// Cleanup
	cudaFree(data_out_gpu);


	printf("GPU results:\n");
	printf(" Sum: %f\n", sum);

	float elapsedTime;
	CUDA_SAFE_CALL(cudaEventElapsedTime(&elapsedTime, start, stop));	// In ms

	double total_time = elapsedTime / 1000.;	// s
	double time_per_iter = total_time / (double)data_size;
	double bandwidth = sizeof(float) / time_per_iter; // B/s

	printf(" Total time: %g s,\n Per iteration: %g ns\n Throughput: %g GB/s\n",
		total_time,
		time_per_iter * 1.e9,
		bandwidth / 1.e9);

	CUDA_SAFE_CALL(cudaEventDestroy(start));
	CUDA_SAFE_CALL(cudaEventDestroy(stop));
	return 0;
}
