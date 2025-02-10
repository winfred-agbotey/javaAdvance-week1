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

/*
* Explanation
Matrix Transposition: The transpose method converts matrix B into Bt, where rows and columns are swapped.
* This allows for more efficient memory access patterns during multiplication.

Recursive Task Splitting: The MultiplyTask class extends RecursiveAction and splits the rows of matrix A into smaller chunks.
* If the chunk size exceeds the threshold, it splits the task into two subtasks and processes them in parallel using invokeAll.

Sequential Computation: When the chunk size is within the threshold, the computeSequentially method multiplies the rows of A with the corresponding rows of Bt (columns of B) to compute the result matrix C.

Fork/Join Framework: The main multiply method initializes the ForkJoinPool and starts the top-level task, which recursively splits the work until the threshold is reached, ensuring efficient parallel execution.
* */