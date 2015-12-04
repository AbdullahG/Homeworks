#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/time.h>
#include <unistd.h>
#define N 1000000

#define THREADNO 200 // I didnt use that because when it changes, 10 and 100 would be insensitive to new number

struct range
{
    long start;
    long end;
};

float numbers[N];
double sum=0.0;
pthread_mutex_t lock=PTHREAD_MUTEX_INITIALIZER;
void *sumNumbersInRange(void *range);

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


void multiThreaded(int threadNumber, int elementNumber)
{
    struct timeval tvBegin, tvEnd, tvDiff;

    // begin
    gettimeofday(&tvBegin, NULL);

    struct range rangeForThread[threadNumber];
    int d;
    int index = elementNumber / threadNumber;
    int unit = index;

    for(d=0; d<threadNumber; d++)
    {
        rangeForThread[d].start=index - unit;
        rangeForThread[d].end = index;
        index = index + unit;
    }

    pthread_t threads[threadNumber];

    int v;
    sum=0.0;

    for(v=0; v<threadNumber; v++)
    {
        pthread_create(&threads[v],NULL,sumNumbersInRange, &rangeForThread[v]);
    }

    for(v=0; v<threadNumber; v++)
    {
        pthread_join(threads[v],NULL);
    }
    printf("ThreadNO: %3d, ArraySize: %7d, Sum:%19.1f ",threadNumber, elementNumber, sum);
    gettimeofday(&tvEnd, NULL);
    timeval_subtract(&tvDiff, &tvEnd, &tvBegin);
    printf("Time: %ld.%06ld second\n", tvDiff.tv_sec, tvDiff.tv_usec);
}
void main()
{
    long i;

    srand(time(NULL));

    for(i=0; i<N ; i++)
    {
        numbers[i] = rand();
    }

    int f = fork();
    if(f==0)
    {
        multiThreaded(10,1000);
        multiThreaded(10,10000);
        multiThreaded(10,100000);
        multiThreaded(10,1000000);

        multiThreaded(100,1000);
        multiThreaded(100,10000);
        multiThreaded(100,100000);
        multiThreaded(100,1000000);

        multiThreaded(200,1000);
        multiThreaded(200,10000);
        multiThreaded(200,100000);
        multiThreaded(200,1000000);

        pthread_mutex_destroy(&lock);
        close(f);
    }

}
void *sumNumbersInRange(void *rangeStruct)
{
    pthread_mutex_lock(&lock);
    struct range *r = (struct range *)rangeStruct;
    int i=0;
    int st = r->start;
    int endd= r->end;
    for(i=st; i<endd; i++)
    {
        sum+=numbers[i];
    }
    pthread_mutex_unlock(&lock);
    pthread_exit(NULL);
}



