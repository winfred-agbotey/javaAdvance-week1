package com.mawulidev;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        //Exercise 1
//        System.out.println("Hello, World!");
//
//        Fibonacci fibonacci = new Fibonacci(40);
//        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
//            System.out.println(forkJoinPool.invoke(fibonacci));
//        }

        //Exercise 2
        int[] array = {10,10,10,10};
        SumTask sumTask = new SumTask(array, 0, array.length);
        try(ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            System.out.println(forkJoinPool.invoke(sumTask));
        }
    }
}