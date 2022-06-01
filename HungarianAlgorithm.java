import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HungarianAlgorithm {
    int[][] matrix; // initial matrix (cost matrix)
    int[][] matrixCopy;

    // markers in the matrix
    int[] starInRow;
    int[] starInCol;
    int[] rowCoveredIndexes;
    int[] colCoveredIndexes;
    int[] zeroPrimeIndexRow;

    public HungarianAlgorithm(int[][] matrix) {

        this.matrix = matrix;
        matrixCopy = new int[matrix.length][matrix.length];
        rowCoveredIndexes = new int[matrix.length];

        //Binary vector, if col[0] = 1, means column has a line over it.
        colCoveredIndexes = new int[matrix.length];

        //Indicates where our zero prime is located in row
        zeroPrimeIndexRow = new int[matrix.length];

        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix.length;j++){
                matrixCopy[i][j] = matrix[i][j];
            }
        }
        starInRow = new int[matrix.length];
        starInCol = new int[matrix.length];
        Arrays.fill(zeroPrimeIndexRow, -1);
    }

    /**
     * https://en.wikipedia.org/wiki/Hungarian_algorithm
     * The hungarian algorithm, finds the assignment s.t.
     * the sum of manhattan distances is minimal.
     *
     *
     * @return
     */
    public int findMinDistance() {
        ////////////////////////STEP 1 ///////////////////////////////////////////////////
        /*
        1.We subtract each the minimum of each row from the row.
        2.We do the same for col.
        3.cover columns with starred zero.
         */
        Initialalize();
        drawLine();
        ////////////////////////////////////////////////////////////////////////////////
        while (!allColumnsAreCovered()) {
            //This will return a tuple of (row, index)
            int[] zeroPrimeIndex = getZeroPrimeIndex();
            while (zeroPrimeIndex == null) {
                subtractMin();
                zeroPrimeIndex = getZeroPrimeIndex();
            }
            if (starInRow[zeroPrimeIndex[0]] == -1) {
                ZeroPrimeStarList(zeroPrimeIndex);

                Arrays.fill(zeroPrimeIndexRow, -1);
                Arrays.fill(colCoveredIndexes, 0);
                Arrays.fill(rowCoveredIndexes, 0);

                drawLine();
            } else {
                rowCoveredIndexes[zeroPrimeIndex[0]] = 1;
                colCoveredIndexes[starInRow[zeroPrimeIndex[0]]] = 0;
                subtractMin();
            }
        }
        int sum=0;
        for (int i = 0; i < starInCol.length; i++) {
            sum += matrixCopy[i][starInCol[i]];
        }
        return sum;
    }

    /**
     * Find a non-covered zero and prime it. If the zero is on the same line as a starred zero,
     * cover the corresponding line, and uncover the column of the starred zero.
     * @return
     */
    public int[] getZeroPrimeIndex() {
        int [] indexArray = new int[2];
        for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j] == 0 && colCoveredIndexes[j] == 0 && rowCoveredIndexes[i] == 0) {
                        zeroPrimeIndexRow[i] = j; // zero prime
                        indexArray[0] = i;
                        indexArray[1] = j;
                        return indexArray;
                    }
                }

        }
        return null;
    }
    /**
     * Perform first and second step in Hungarian algorithm.
     *
     */
    public void Initialalize() {
        // rows
        for (int i = 0; i < matrix.length; i++) {
            // find the min value of the current row
            int minRow = Integer.MAX_VALUE;
            for (int j = 0; j < matrix[i].length; j++) {
                minRow = Math.min(matrix[i][j], minRow);
            }
            // subtract min value from each element of the current row
            for (int k = 0; k < matrix[i].length; k++) {
                matrix[i][k] -= minRow;
            }
        }

        // cols
        for (int i = 0; i < matrix[0].length; i++) {
            // find the min value of the current column
            int minCol = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                minCol = Math.min(matrix[i][j], minCol);
            }
            // subtract min value from each element of the current column
            for (int k = 0; k < matrix.length; k++) {
                matrix[k][i] -= minCol;
            }
        }

        //////////////////////////STEP 2 ///////////////////////////////
        int[] rowIsTaken = new int[matrix.length];
        int[] colIsTaken = new int[matrix.length];

        //Initialize
        for(int i=0;i<matrix.length;i++){
            starInCol[i] = -1;
            starInRow[i] = -1;
        }
        //assign as many tasks as possible. The assigned tasks are represented by starring a zero.
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                // for each zero we find , we mark in row and col
                if (matrix[i][j] == 0 && rowIsTaken[i] == 0 && colIsTaken[j] == 0) {
                    rowIsTaken[i] = 1;
                    colIsTaken[j] = 1;

                    starInRow[i] = j; // save the row-position of the zero
                    starInCol[j] = i; // save the column-position of the zero

                }
            }
        }
    }

    /**
     * We draw the line over each col.
     * Cover all columns having an assignment
     */
    private void drawLine() {
        for (int i = 0; i < starInCol.length; i++) {
            //if we have a starred zero in col, we cover it.
            // colCoveredIndexes = [1,0,1,0] means we cover columns 0 and 2 respectively.
            if(starInCol[i] != -1){
                colCoveredIndexes[i] = 1;
            }
            else {
                colCoveredIndexes[i] = 0;
            }
        }
    }

    /**
     * Step 7:
     * 1. Find the smallest uncovered value in the matrix.
     * 2. Subtract it from all uncovered values
     * 3. Add it to all twice-covered values
     */
    private void subtractMin() {
        // Find min uncovered value.
        int minUncoveredValue = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if (rowCoveredIndexes[i] != 1) {
                for (int j = 0; j < matrix.length; j++) {
                    if (colCoveredIndexes[j] == 0) {
                        minUncoveredValue = Math.min(minUncoveredValue,matrix[i][j]);
                    }
                }
            }
        }

        if (minUncoveredValue != 0) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (rowCoveredIndexes[i] == 1 && colCoveredIndexes[j] == 1) {
                        // Add min to all twice-covered values
                        matrix[i][j] += minUncoveredValue;
                    }
                    if (rowCoveredIndexes[i] == 0 && colCoveredIndexes[j] == 0) {
                        // Subtract min from all uncovered values
                        matrix[i][j] -= minUncoveredValue;
                    }
                }
            }
        }
    }

    /**
     * create an alternating set of zero primes and zero*
     * @param zeroIndex
     */
    private void ZeroPrimeStarList(int[] zeroIndex) {

        int index = zeroIndex[1];

        Set<int[]> zeroSet = new HashSet<>();
        boolean flag = true;
        zeroSet.add(zeroIndex);

        int i;
        while(flag){
            if(starInCol[index] != -1){
                int [] indexArray = {starInCol[index], index};
                zeroSet.add(indexArray);
            }
            else {
                break;
            }
            i = starInCol[index];
            index = zeroPrimeIndexRow[i];
            if (index != -1) {
                int [] indexArray = {i, index};
                zeroSet.add(indexArray);
            } else {
                break;
            }
        }
        removeZeroStar(zeroSet);

    }

    /**
     * Remove all 0*, 0' and put stars instead.
     * @param zeros
     */
    public void removeZeroStar(Set<int []> zeros){
        for (int[] zero : zeros) {
            // remove all 0*
            if (starInCol[zero[1]] == zero[0]) {
                starInCol[zero[1]] = -1;
                starInRow[zero[0]] = -1;
            }
            // remove all primes and put 0* instead.
            if (zeroPrimeIndexRow[zero[0]] == zero[1]) {
                starInRow[zero[0]] = zero[1];
                starInCol[zero[1]] = zero[0];
            }
        }
    }
    /**
     * Check if all columns are covered. If that's the case then the
     * optimal solution is found
     *
     * @return true or false
     */
    public boolean allColumnsAreCovered() {
        for (int i = 0; i < colCoveredIndexes.length; i++){
            if (colCoveredIndexes[i] == 0) {
                return false;
            }
        }
        return true;
    }

}
