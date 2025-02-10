package com.mawulidev;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelBlockMatrixMultiplier {
    private static final int BLOCK_SIZE = 64; // Tune block size based on testing

    public static void multiply(int[][] A, int[][] B, int[][] C) {
        if (A.length == 0 || B.length == 0) return;
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("Matrices are not compatible for multiplication.");
        }
        int[][] Bt = transpose(B);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new BlockMultiplyTask(A, Bt, C, 0, A.length, 0, C[0].length));
    }

    private static int[][] transpose(int[][] matrix) {
        var rows = matrix.length;
        var cols = matrix[0].length;
        var transposed = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }

    private static class BlockMultiplyTask extends RecursiveAction {
        private final int[][] A, Bt, C;
        private final int rowStart, rowEnd;
        private final int colStart, colEnd;

        BlockMultiplyTask(int[][] A, int[][] Bt, int[][] C, int rowStart, int rowEnd, int colStart, int colEnd) {
            this.A = A;
            this.Bt = Bt;
            this.C = C;
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.colStart = colStart;
            this.colEnd = colEnd;
        }

        @Override
        protected void compute() {
            int rowLength = rowEnd - rowStart;
            int colLength = colEnd - colStart;

            if (rowLength <= BLOCK_SIZE && colLength <= BLOCK_SIZE) {
                computeBlock();
            } else {
                if (rowLength >= colLength) {
                    int midRow = (rowStart + rowEnd) / 2;
                    invokeAll(
                            new BlockMultiplyTask(A, Bt, C, rowStart, midRow, colStart, colEnd),
                            new BlockMultiplyTask(A, Bt, C, midRow, rowEnd, colStart, colEnd)
                    );
                } else {
                    int midCol = (colStart + colEnd) / 2;
                    invokeAll(
                            new BlockMultiplyTask(A, Bt, C, rowStart, rowEnd, colStart, midCol),
                            new BlockMultiplyTask(A, Bt, C, rowStart, rowEnd, midCol, colEnd)
                    );
                }
            }
        }

        private void computeBlock() {
            int colsA = A[0].length; // Equals rows in original B
            for (int i = rowStart; i < rowEnd; i++) {
                for (int j = colStart; j < colEnd; j++) {
                    int sum = 0;
                    for (int k = 0; k < colsA; k++) {
                        sum += A[i][k] * Bt[j][k];
                    }
                    C[i][j] = sum;
                }
            }
        }
    }

}

/*
* Explanation
Matrix Transposition: The transpose method converts matrix B into Bt, where rows and columns are swapped.
* This allows for more efficient memory access patterns during multiplication.
Recursive Task Splitting: The BlockMultiplyTask class extends RecursiveAction and splits the result matrix C into smaller blocks.
* If the block size exceeds the threshold, it splits the task into subtasks (either row-wise or column-wise) and processes them in parallel using invokeAll.

Sequential Computation: When the block size is within the threshold, the computeBlock method multiplies the corresponding rows of A with the rows of Bt (columns of B) to compute the result block in C.

Fork/Join Framework: The main multiply method initializes the ForkJoinPool and starts the top-level task, which recursively splits the work until the threshold is reached, ensuring efficient parallel execution.
* */