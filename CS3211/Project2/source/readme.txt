======================
How to compile and run
======================

1. load crypto_sieve.c & crypto_trial.c onto tembusu

2. access one of the sma nodes

3. compile crypto_sieve.c & crypto_trial.c
   mpicc crypto_sieve.c -o crypto_sieve
   mpicc crypto_trial.c -o crypto_trial

4. run crypto_sieve
   mpirun -np <npes> ./crypto_sieve <p> <floor> <ceiling>
     where
     <npes> is the number of processes
     <p> is the prime number
     <floor> is the lower bound of generators to print out
     <ceiling> is the upper bound of generators to print out

   (e.g. mpirun -np 4 ./crypto_sieve 197 10 50)

   run crypto_trial
   mpirun -np <npes> ./crypto_trial <p> <floor> <ceiling>

*Note: crypto_sieve & crypto_trial are different implementations
       crypto_sieve utilize Sieve of Eratosthenes algorithm
       crypto_trial utilize Trial Division algorithm 