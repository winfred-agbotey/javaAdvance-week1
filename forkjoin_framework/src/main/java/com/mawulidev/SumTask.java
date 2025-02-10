package com.mawulidev;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
    private final int[] array;
    private final int start;
    private final int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0L;
        int THRESHOLD = 100;
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
        int middle = (start + end) >>> 1;
        SumTask left = new SumTask(array, start, middle);
        SumTask right = new SumTask(array, middle, end);
        left.fork();
        right.fork();

        return right.join() + left.join();
    }


}
