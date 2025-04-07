package com.mawulidev;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
    private static final ConcurrentHashMap<Integer, Long> memo = new ConcurrentHashMap<>();

    private final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        // Check if result already computed
        if (memo.containsKey(n)) {
            return Math.toIntExact(memo.get(n));
        }

        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);

        long result = f2.compute() + f1.join();
        memo.put(n, result);
        return Math.toIntExact(result);


    }
}
