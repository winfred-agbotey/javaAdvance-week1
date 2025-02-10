package com.mawulidev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static com.mawulidev.ParallelMatrixMultiplier.multiply;

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
//        int[] array = {10,10,10,10};
//        SumTask sumTask = new SumTask(array, 0, array.length);
//        try(ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
//            System.out.println(forkJoinPool.invoke(sumTask));
//        }

        //Exercise 3
        int[][] A = {{1, 2}, {3, 4}};
        int[][] B = {{5, 6}, {7, 8}};
        int[][] C = new int[A.length][B.length];
        multiply(A, B, C);
        System.out.println("Result of parallel multiplication:");
        System.out.println(Arrays.deepToString(C)); // Should print [[19, 22], [43, 50]]


        //Exercise 4
        int[][] exA = {{1, 2}, {3, 4}};
        int[][] exB = {{5, 6}, {7, 8}};
        int[][] results = new int[exA.length][exB.length];
        multiply(exA, exB, results);
        System.out.println("Result of parallel block multiplication:");
        System.out.println(Arrays.deepToString(C)); // Output: [[19, 22], [43, 50]]
    }
}