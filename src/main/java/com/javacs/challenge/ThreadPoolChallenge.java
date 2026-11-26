package com.javacs.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolChallenge {

    private final int nThreads;

    public ThreadPoolChallenge(int nThreads) {
        this.nThreads = nThreads;
    }
    public void threadPoolSum() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 100; i++)
            numbers.add(i);

        ExecutorService executorService = Executors.newFixedThreadPool(this.nThreads);

        int arrayTotalSize = numbers.size();
        int chunkSize = (int) Math.ceil((double) arrayTotalSize / nThreads); // 34 34 32

        List<Future<Long>> futureList = new ArrayList<>();

        for (int start = 0; start < arrayTotalSize; start += chunkSize) {
            int end = Math.min(start + chunkSize, arrayTotalSize);
            List<Integer> subList = numbers.subList(start, end);

            Callable<Long> task = () -> {
                long sum = 0;
                for (int n : subList)
                    sum += n;

                return sum;
            };
        }
    }
}
