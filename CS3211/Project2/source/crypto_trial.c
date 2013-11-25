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
    // long long numPrime = findPrimeNumbers(target,npes,myrank);
    // round up integer division
    long long offset = (target+npes-1)/npes;
    long long *factors = (long long*)malloc(sizeof(long long)*target);
    if (factors==NULL) exit(1);
    long long *buffer = (long long*)malloc(sizeof(long long)*target);
    if (buffer==NULL) exit(1);
    
    int numFactors=0;
    long long bound = ceil(sqrt(p-1));
    
    if (myrank==0)
    {
        long long start,end;

        unsigned int x;

        // trial division method
        long long *prime = (long long *)malloc(npes*2*sizeof(long long));

        if (target%2==0)
        {
            prime[0] = 2;
            factors[numFactors++] = 2;
        }
        if (target%3==0)
        {
            prime[1] = 3;
            factors[numFactors++] = 3;
        }

        int primesize=numFactors;
        long long pseudoprime[3];
        long long pseudoreply[2];
        int i=1;
        int pseudoseed;
        do
        {
            int y;
            for (y=1;y<npes;y++)
            {
               // printf("primesize:%d:%d\n",primesize,prime[0]);
                MPI_Send((void*)prime,primesize,MPI_LONG_LONG_INT,y,0,MPI_COMM_WORLD);
            }
            primesize=0;
            for (x=1;x<npes;x++)
            {
                // send additional primes to slaves
                pseudoseed = i+x-1;
                pseudoprime[0] = 6 * pseudoseed - 1;
                pseudoprime[1] = 6 * pseudoseed + 1;
                pseudoprime[2] = 6 * (i + npes - 1) + 1;
              //  printf("%d:%d:%d\n",x,pseudoprime[0],pseudoprime[1]);

                MPI_Send((void*)pseudoprime,3,MPI_LONG_LONG_INT,x,0,MPI_COMM_WORLD);
            }
            // recieve the results
            for (x=1;x<npes;x++)
            {
              //  printf("%d:in recv\n",x);
                MPI_Recv((void*)pseudoreply,2,MPI_LONG_LONG_INT,x,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
              //  printf("out recv\n");
                int y;
                for (y=0;y<2;y++)
                {
                    if (pseudoreply[y])
                    {
                        prime[primesize++] = pseudoreply[y];
                        factors[numFactors++] = pseudoreply[y];
                    }
                }
            }
            i += npes - 1;
        } while (pseudoprime[1] < bound);
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
        // printf("my result %d\n",resultTotal); 
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
        // recieve additional primes
        int addPrime;
        long long pseudo[3];
        long long reply[2];
        numFactors=0;
        do
        {
            MPI_Recv((void*)buffer,8,MPI_LONG_LONG_INT,0,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
            MPI_Get_count(&stat,MPI_LONG_LONG_INT,&addPrime);
            //printf("proc:%d:prime:%d:%d\n",myrank,addPrime,buffer[0]);
            for (x=0;x<addPrime;x++)
            {
                factors[numFactors++] = buffer[x]; 
            //    printf("proc:%d:fac:%d\n",myrank,factors[numFactors-1]);
            }

            // recieve the 2 pseudoprimes...
            MPI_Recv((void*)pseudo,3,MPI_LONG_LONG_INT,0,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
            for (x=0;x<2;x++)
            {
            //    printf("proc:%d:pseudo:%d\n",myrank,pseudo[x]);
                int flag=0;
                if (target%pseudo[x]==0)
                {
                    flag = 1;
                    int y;
                    for (y=0;flag && y<numFactors;y++)
                    {
                        if (pseudo[x]%factors[y]==0)
                        {
                            flag=0;
                        }
                    }
                }
                if (flag)
                {
                    reply[x] = pseudo[x];
                }
                else
                {
                    reply[x] = 0;
                }
            }
        //    printf("proc:%d:send\n",myrank);
            MPI_Send((void*)reply,2,MPI_LONG_LONG_INT,0,0,MPI_COMM_WORLD);
          //  printf("proc:%d:send\n",myrank);
        } while (pseudo[2] < bound);
        
        // get the size of factors...
        // let's wait for the new set of factors to crunch numbers...
        MPI_Recv((void*)factors,target,MPI_LONG_LONG_INT,0,MPI_ANY_TAG,MPI_COMM_WORLD,&stat);
        int factorSize;
        MPI_Get_count(&stat,MPI_LONG_LONG_INT,&factorSize);
        // printf("proc recieved %d\n",factorSize);
        long long start,end,offset; 
        offset = (p+npes-1)/npes;
        start = offset * (myrank-1);
        end = start + offset;
        if (start==0) start=2; 

        int generatorSize = findGen(start,end,factorSize,factors);
        // printf("%d has %d\n",myrank,generatorSize);
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

