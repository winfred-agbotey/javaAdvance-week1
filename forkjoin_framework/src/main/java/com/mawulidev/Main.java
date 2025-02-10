package com.mawulidev;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Fibonacci fibonacci = new Fibonacci(40);
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            System.out.println(forkJoinPool.invoke(fibonacci));
        }
    }
}