
// GPU kernel
__global__ void summation_kernel(int data_size, float * data_out)
{
	unsigned int id = blockIdx.x * blockDim.x + threadIdx.x;
	unsigned int nb_threads_total = blockDim.x * gridDim.x;
	unsigned int units_per_thread = data_size / nb_threads_total;
	int i;
	float res = 0.;

	printf("Before launching the GPU thread id:%d nb threads:%d units per thread:%d \n", id, nb_threads_total, units_per_thread);
	for (i = ((id+1) * units_per_thread)-1; i >= int(id * units_per_thread); i--) {
		if(id==0)printf("boucle:%d",i);
        if(i%2==0) res += 1.0/(i+1.0);
        else res -= 1.0/(i+1.0);
	}
	printf("Getting the result for thread %d which is %f \n", id, res);
	data_out[id] = res;
	printf("Thread %d has ended\n", id);
}
