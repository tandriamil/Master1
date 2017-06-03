// GPU kernel
__global__ void summation_kernel(int data_size, float * data_out) {

	// Get the id of this thread in the whole thread group
	unsigned int id = blockIdx.x * blockDim.x + threadIdx.x;

	// Get the total number of threads in the whole thread group
	unsigned int nb_threads_total = blockDim.x * gridDim.x;

	// Get the number of part for each thread
	unsigned int units_per_thread = nb_threads_total / data_size;

	// Compute datas for this thread
	int i;
	float res = 0;
	for (i = ((id+1) * units_per_thread)-1; i >= (id * units_per_thread); --i) {
		if (i%2==0) res += 1.0/(i+1.0);
		else res -= 1.0/(i+1.0);
	}

	// Store the result
	data_out[id] = res;
}
