#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/time.h>
#include <unistd.h>
#define N 1000000

float numbers[N];
double sum=0.0;
void *sumNumbers();
void timeval_print(struct timeval *tv)
{
    printf("%06ld microseconds\n", tv->tv_usec);
}

int timeval_subtract(struct timeval *result, struct timeval *t2, struct timeval *t1)
{
    long int diff = (t2->tv_usec + 1000000 * t2->tv_sec) - (t1->tv_usec + 1000000 * t1->tv_sec);
    result->tv_sec = diff / 1000000;
    result->tv_usec = diff % 1000000;

    return (diff<0);
}

void main()
{
    long i;

    srand(time(NULL));

    for(i=0; i<N ; i++)
    {
        numbers[i] = rand();
    }

    pthread_t tid;

    int f=fork();

    if(f==0){

    pthread_create(&tid, NULL, sumNumbers, NULL);

    close(f);
    }

    pthread_exit(NULL);
}

void *sumNumbers()
{
    struct timeval tvBegin, tvEnd, tvDiff;

    // begin
    gettimeofday(&tvBegin, NULL);

    int i;

    for(i=0; i<1000; i++)
        sum+=numbers[i];

    gettimeofday(&tvEnd, NULL);
    timeval_subtract(&tvDiff, &tvEnd, &tvBegin);
    printf("Array Size: 1000,    Sum: %f,    Time: ",sum);
    printf("%ld.%06ld second\n", tvDiff.tv_sec, tvDiff.tv_usec);

    for(; i<10000; i++)
        sum+=numbers[i];

    gettimeofday(&tvEnd, NULL);
    timeval_subtract(&tvDiff, &tvEnd, &tvBegin);
    printf("Array Size: 10000,   Sum: %f,   Time: ",sum);
    printf("%ld.%06ld second\n", tvDiff.tv_sec, tvDiff.tv_usec);

    for(; i<100000; i++)
        sum+=numbers[i];

    gettimeofday(&tvEnd, NULL);
    timeval_subtract(&tvDiff, &tvEnd, &tvBegin);
    printf("Array Size: 100000,  Sum: %f,  Time: ",sum);
    printf("%ld.%06ld second\n", tvDiff.tv_sec, tvDiff.tv_usec);

    for(; i<1000000; i++)
        sum+=numbers[i];

    gettimeofday(&tvEnd, NULL);
    timeval_subtract(&tvDiff, &tvEnd, &tvBegin);
    printf("Array Size: 1000000, Sum: %f, Time: ",sum);
    printf("%ld.%06ld second\n", tvDiff.tv_sec, tvDiff.tv_usec);

    pthread_exit(NULL);
}

