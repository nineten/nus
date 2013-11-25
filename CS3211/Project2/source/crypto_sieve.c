#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
#include <sys/times.h>

#define MIN(a,b)  ((a)<(b)?(a):(b))
#define BLOCK_LOW(id,p,n)  ((id)*(n)/(p))
#define BLOCK_HIGH(id,p,n)   (BLOCK_LOW(((id)+1),p,n)-1)
#define BLOCK_SIZE(id,p,n)   ((BLOCK_LOW(((id)+1),p,n))-(BLOCK_LOW(id,p,n)))
#define BLOCK_OWNER(index,p,n)   (((p)*(index)+1)-1)/(n))

long long p;

// declaration for sieve
int global_count;           // global count of prime numbers
long long *global_prime;          // array of all primes
int *process_prime_count;   // array of counts of element in each process 
int *process_prime_displ;   // array of displacement to allocate

long long min,max;

long long findPrimeNumbers(long long p,int npes, int myrank);

int main(int argc, char *argv[])
{
    int npes, myrank;
    p = atoi(argv[1]);
    min = atoi(argv[2]);
    max = atoi(argv[3]);
    long long local;
    long long target = p-1;
    
    struct tms b4,aft;
    int cps;
    float tim,total,max;
    clock_t tm;

    MPI_Status stat;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &npes);
    MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
    times(&b4);

    // finding factors for p-1
    // let's let all the cores generate the factors.. and send back to
    // the master node...

    // finding all the prime numbers
    long long numPrime = findPrimeNumbers(target,npes,myrank);
    // round up integer division
    long long offset = (target+npes-1)/npes;
    long long *factors = (long long*)malloc(sizeof(long long)*target);
    if (factors==NULL) exit(1);
    long long *buffer = (long long*)malloc(sizeof(long long)*target);
    if (buffer==NULL) exit(1);
    
    int numFactors=0;
    
    if (myrank==0)
    {
        long long start,end;

        unsigned int x;

        long long prime_domain = (numPrime+npes-1) / npes;
        long long *current_prime = global_prime;

        for (x=1;x<npes;x++)
        {
            MPI_Send((void *)current_prime, prime_domain, MPI_LONG_LONG_INT, x, 0, MPI_COMM_WORLD);
            current_prime += prime_domain;
        }
        start = (npes-1)*prime_domain;

        // calculate my fair share of work
        for (x=start;x<numPrime;x++)
        {
            if (target%global_prime[x]==0)
            {
                factors[numFactors] = global_prime[x];
                numFactors++;
            }
        }
        // i'm done... waiting for slaves and consolidating values
        for (x=1;x<npes;x++)
        {
            MPI_Recv((void*)buffer,target,MPI_LONG_LONG_INT,x,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
            int factorCount;
            MPI_Get_count(&stat,MPI_LONG_LONG_INT,&factorCount);
            
            int y;
            for (y=0;y<factorCount;y++)
            {
                factors[numFactors] = buffer[y];
                numFactors++;
            }
        }
        
        // recieved all the factors... now we can redistribute the factors and let our slaves do some number crunching...
        for (x=1;x<npes;x++)
        {
            // send factors for each machine

            // notify each node about the size of the factor
            // to enable them to prep memory for it
            MPI_Send((void*)factors,numFactors,MPI_LONG_LONG_INT,x,0,MPI_COMM_WORLD);
        }

        // crunch some number on our side...
        offset = (p+npes-1)/npes;
        unsigned int resultTotal = 0;
        start = offset*(npes-1);
        if (start==0) start=2;
        
        resultTotal += findGen(start,p,numFactors,factors);
        
        // consolidate results
        unsigned int recvResult;
        
        for (x=1;x<npes;x++)
        {
            MPI_Recv((void*)&recvResult,1,MPI_INT,x,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
            resultTotal += recvResult;
        }

        //we're done.. print results..

        printf("Generator Size:%d\n",resultTotal);
    }
    else
    {
        int x;
        int numFactors = 0;

        // recieve prime list from master and test for factors
        MPI_Recv((void*)buffer,target,MPI_LONG_LONG_INT,0,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
        int numPrime;
        MPI_Get_count(&stat,MPI_LONG_LONG_INT,&numPrime);
        
        for (x=0;x<numPrime;x++)
        {
            if (target%buffer[x]==0)
            {
                // buffer[x] is a factor
                factors[numFactors] = buffer[x];
                numFactors++;
            }
        }
        // done calculating, lets send back to the master
        MPI_Send((void*)factors,numFactors,MPI_LONG_LONG_INT,0,0,MPI_COMM_WORLD);
        
        // get the size of factors...
        // let's wait for the new set of factors to crunch numbers...
        MPI_Recv((void*)factors,target,MPI_LONG_LONG_INT,0,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
        int factorSize;
        MPI_Get_count(&stat,MPI_LONG_LONG_INT,&factorSize);
        long long start,end,offset; 
        offset = (p+npes-1)/npes;
        start = offset * (myrank-1);
        end = start + offset;
        if (start==0) start=2; 

        int generatorSize = findGen(start,end,factorSize,factors);

        // we're done for our part... send back result to server
        MPI_Send((void*)&generatorSize,1,MPI_INT,0,0,MPI_COMM_WORLD);

    }

    times(&aft);
    cps = sysconf(_SC_CLK_TCK);
    tm = aft.tms_utime - b4.tms_utime;
    tim = ((float)tm)/cps;
    printf( "User time from process %d is %6.3f seconds\n",myrank,tim );
    
    MPI_Reduce((void *)&tim,(void *)&total,1,MPI_FLOAT,MPI_SUM,0,MPI_COMM_WORLD);
    MPI_Reduce((void *)&tim,(void *)&max,1,MPI_FLOAT,MPI_MAX,0,MPI_COMM_WORLD);
    if (myrank==0)
    {
        printf("Total time is %6.3f seconds\n",total);
        printf("Maximum time is %6.3f seconds\n",max);
    }
    
    MPI_Finalize();
    return 0;
}

int modexp (long long t,long long u,long long n)
{
    long long lt, lu, ln;
    lt = t;
    lu = u;
    ln = n;
    // computes s = (t ^ u) mod n
    long long ls = 1;
    while (lu)
    {
        if (lu & 1)
        {
            ls = (ls * lt) % ln;
        }
        lu >>= 1;
        lt = (lt * lt) % ln;
    }
    return ls;
}

int findGen(long long start, long long end,int factorSize, long long *myFactor)
{
    int x;
    int generatorSize=0;
    for (x=start;x<end;x++)
    {
        int gen = 1;
        int k;
        for (k=0; k<factorSize; k++)
        {
            if (modexp(x, (p-1)/myFactor[k], p)==1)
            {
                gen=0;
                break;
            }
        }
        if (gen)
        {
            if (x>=min && x<=max)
                printf("A generator for %d is %d.\n",p,x);
            generatorSize++;
        }
    }
    return generatorSize;
}

long long findPrimeNumbers(long long p,int npes, int myrank)
{
    // find Prime Numbers using Sieve of Eratosthenes

    // declaration
    int local_count;            // local prime count
    long long first_sieve;            // index of the first sieve
    long long low_value;              // lowest value assigned to this process
    long long high_value;             // highest value assigned to this process
    long long index;                  // index of current sieve
    long long *marked;                // array of elements to be marked
    long long marked_size;            // size of elements in marked array
    long long proc0_size;             // number of elements assigned to process zero
    long long current_sieve;          // current sieve
    long long *local_prime;           // array of primes found by local process

    low_value = 2 + BLOCK_LOW(myrank,npes,p-1);
    high_value = 2 + BLOCK_HIGH(myrank,npes,p-1);
    marked_size = BLOCK_SIZE(myrank,npes,p-1);
    proc0_size = (p-1)/npes;

    if ((2 + proc0_size) < (long long) sqrt((double)p))
    {
        if (!myrank)
        {
            printf ("Too many processes\n");
            MPI_Finalize();
            exit(1);
        }
    }

    if (!myrank)
    {
        global_prime = (long long *) malloc (p * sizeof(long long));
        if (global_prime == NULL)
        {
            printf ("Cannot allocate enough memory\n");
            MPI_Finalize();
            exit (1);
        }
    }

    local_prime = (long long *) malloc (marked_size * sizeof(long long));
    if (local_prime == NULL)
    {
        printf ("Cannot allocate enough memory\n");
        MPI_Finalize();
        exit (1);
    }

    marked = (long long *) malloc (marked_size * sizeof(long long));
    if (marked == NULL) 
    {
        printf ("Cannot allocate enough memory\n");
        MPI_Finalize();
        exit (1);
    }    

    process_prime_count = (int *)malloc(npes*sizeof(int));
    if (process_prime_count == NULL)
    {
        printf ("Cannot allocate enough memory\n");
        MPI_Finalize();
        exit (1);
    }

    process_prime_displ = (int *)malloc(npes*sizeof(int));
    if (process_prime_displ == NULL)
    {
        printf ("Cannot allocate enough memory\n");
        MPI_Finalize();
        exit (1);
    }

    // initialise the marked array
    unsigned long long i;
    for (i=0; i<marked_size; i++)
    {
        marked[i] = 0;
    }

    if (!myrank)
    {
        index = 0;
    }

    current_sieve = 2; 
    do
    {
        if (current_sieve * current_sieve > low_value)
        {
            first_sieve = current_sieve * current_sieve - low_value;
        }
        else
        {
            if (!(low_value % current_sieve))
            {
                first_sieve = 0;
            }
            else
            {
                first_sieve = current_sieve - (low_value % current_sieve);
            }
        }

        for (i = first_sieve; i < marked_size; i+=current_sieve)
        {
            marked[i] = 1;
        }

        if (!myrank)
        {
            while (marked[++index]);
            current_sieve = index + 2;
        }

        MPI_Bcast (&current_sieve, 1, MPI_LONG_LONG_INT, 0, MPI_COMM_WORLD);
    } while (current_sieve * current_sieve <= p);

    local_count = 0;
    for (i = 0; i < marked_size; i++)
    {
        if (!marked[i])
        {
            local_prime[local_count] = i + low_value;
            local_count++;
        }
    }

    MPI_Reduce (&local_count, &global_count, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    MPI_Gather (&local_count, 1,MPI_INT,process_prime_count,1,MPI_INT,0,MPI_COMM_WORLD);

    if (!myrank)
    {
        int x;
        for (x=0;x<npes;x++)
        {
            process_prime_displ[x] = 0;
        }

        for (x=1;x<npes;x++)
        {
            process_prime_displ[x] = process_prime_displ[x-1] + process_prime_count[x-1];
        }
    }

    MPI_Gatherv (local_prime, local_count, MPI_LONG_LONG_INT, global_prime, process_prime_count,process_prime_displ, MPI_LONG_LONG_INT, 0, MPI_COMM_WORLD);

/*
    for (i=0; i<global_count; i++)
    {
        printf ("%d ", global_prime[i]);
    }
    printf("\n");
*/
    return global_count;
}
