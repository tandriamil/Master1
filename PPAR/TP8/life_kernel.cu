
__global__ void init_kernel(int * domain, int domain_x)
{
	// Dummy initialization
	domain[blockIdx.y * domain_x + blockIdx.x * blockDim.x + threadIdx.x]
		= (1664525ul * (blockIdx.x + threadIdx.y + threadIdx.x) + 1013904223ul) % 3;
}

// Reads a cell at (x+dx, y+dy)
__device__ int read_cell(int * source_domain, int x, int y, int dx, int dy,
    unsigned int domain_x, unsigned int domain_y)
{
    x = (unsigned int)(x + dx) % domain_x;	// Wrap around
    y = (unsigned int)(y + dy) % domain_y;
    return source_domain[y * domain_x + x];
}

// Compute kernel
__global__ void life_kernel(int * source_domain, int * dest_domain,
    int domain_x, int domain_y, int shared_x, int shared_y)
{

    int tx = blockIdx.x * blockDim.x + threadIdx.x;
    int ty = blockIdx.y;

    // Shared memory
    extern __shared__ int block_row[];

    block_row[threadIdx.y * shared_x + threadIdx.x] = source_domain[];
    __syncthreads();


    // Read cell
    int myself = read_cell(block_row, tx, ty, 0, 0,
	                       domain_x, domain_y);


    //Read the 8 neighbors and count number of blue and red
    int i, j, tmp, value, nbRed = 0, nbBlue = 0;
    for (i = -1 ; i <= 1 ; i++){
        for (j = -1 ; j <= 1 ; j++){
            if(i!=0 || j!=0){
                tmp = read_cell( block_row, tx, ty, i, j, domain_x, domain_y);
                switch(tmp){
                    case 1:{
                        nbRed++;
                        break;
                    }
                    case 2:{
                        nbBlue++;
                        break;
                    }
                }
            }
        }
    }

	//Compute new value

    //DIE
    if ((nbBlue+nbRed<2) || (nbBlue+nbRed>3)) {
        value = 0;
    }

    // New cell
    else if ((myself == 0) && (nbBlue+nbRed==3)) {

        value = (nbBlue > nbRed) ? 2 : 1;

    }

	//Write it in dest_domain
    dest_domain[ty * domain_x + tx] = value;
}
