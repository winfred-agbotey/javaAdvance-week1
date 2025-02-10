package com.mawulidev;

import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
    private final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        } else {
            Fibonacci task1 = new Fibonacci(n - 1);
            Fibonacci task2 = new Fibonacci(n - 2);
            task1.fork();
            task2.fork();

            return task1.join() + task2.join();
        }

    }
}
