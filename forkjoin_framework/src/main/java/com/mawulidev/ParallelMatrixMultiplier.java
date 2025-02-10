package com.mawulidev;

import java.util.concurrent.ForkJoinPool;

public class ParallelMatrixMultiplier {
    private static final int THRESHOLD = 64;

    public static void multiply(int[][] matrixA, int[][] matrixB, int[][] result) {
        if (matrixA.length == 0 || matrixB.length == 0) return;
        if (matrixA.length != matrixB.length) {
            throw new IllegalArgumentException("Matrix lengths don't match");
        }

        int[][] matrixBTransposed = transpose(matrixB);
        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(new MultiplyTask(matrixA, matrixBTransposed, result, 0, matrixA.length, THRESHOLD));
        }

    }

    private static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }
}
