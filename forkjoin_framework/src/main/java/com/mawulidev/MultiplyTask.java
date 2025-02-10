package com.mawulidev;

import java.util.concurrent.RecursiveAction;

public class MultiplyTask extends RecursiveAction {
    private final int[][] matrixA;
    private final int[][] matrixBTransposed;
    private final int[][] matrixC;
    private final int startRow;
    private final int endRow;
    private final int threshold;

    public MultiplyTask(int[][] matrixA, int[][] matrixBTransposed, int[][] matrixC, int startRow, int endRow, int threshold) {
        this.matrixA = matrixA;
        this.matrixBTransposed = matrixBTransposed;
        this.matrixC = matrixC;
        this.startRow = startRow;
        this.endRow = endRow;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        int length = endRow - startRow;
        if (length <= threshold) {
            computeSequentially();
        } else {
            int midRow = (startRow + length) / 2;
            MultiplyTask left = new MultiplyTask(matrixA, matrixBTransposed, matrixC, startRow, midRow, threshold);
            MultiplyTask right = new MultiplyTask(matrixA, matrixBTransposed, matrixC, midRow, endRow, threshold);
            invokeAll(left, right);

        }

    }

    private void computeSequentially() {
        int colsC = matrixC[0].length;
        int kMax = matrixA[0].length; // Number of columns in A (rows in original B)
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < colsC; j++) {
                int sum = 0;
                for (int k = 0; k < kMax; k++) {
                    sum += matrixA[i][k] * matrixBTransposed[j][k];
                }
                matrixC[i][j] = sum;
            }
        }

    }
}
