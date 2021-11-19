/** A class that implements Strassen's Matrix Multiplication algorithm. 
 * @author Jessica Margala
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class strassenMatrixMultiplication {
	
	/**
	 * Method to generate Matrix filled with random values from 0 - 10 (for testing)
	 * @param n (desired size of matrix)
	 * @return new filled matrix of size nxn
	 */
	static int[][] generateMatrix(int n) {
		
		Random rand = new Random();
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = rand.nextInt(10);
			}
		}
		return matrix;
	}
	
	/**
	 * Method to print out matrix for testing to ensure correct values are calculated
	 * @param matrix (matrix to be printed)
	 * @param n (size of the matrix)
	 */
	static void printMatrix(int [][] matrix, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(matrix[i][j]+ " ");
			System.out.println();
		}
	}
	
	/**
	 * Method to divide matrices further into 4 halves
	 * @param parent (the desired matrix to be divided)
	 * @param child (new sub-matrix from the parent)
	 * @param k (initial row position)
	 * @param m (initial column position)
	 */
	static void strassenDivide(int parent[][],  int child[][], int k, int m) {
		// Rows
		for(int i1 = 0, i2 = k; i1 < child.length; i1++, i2++)
			// Columns
            for(int j1 = 0, j2 = m; j1 < child.length; j1++, j2++)
                child[i1][j1] = parent[i2][j2];
	}
	
	/**
	 * Method to merge sub-matrices back with original matrix
	 * @param child (the desired sub-matrix to be merged)
	 * @param parent (newly merged matrix)
	 * @param k (initial row position)
	 * @param m (initial column position)
	 */
	static void strassenMerge(int child[][],  int parent[][], int k, int m) {
		for(int i1 = 0, i2 = k; i1 < child.length; i1++, i2++)
            for(int j1 = 0, j2 = m; j1 < child.length; j1++, j2++)
                parent[i2][j2] = child[i1][j1];
	}
	
	/**
	 * Method to add matrices A and B
	 * @param A (first matrix to be added)
	 * @param B (second matrix to be added)
	 * @return updated solution matrix C
	 */
	static int[][] strassenAdd(int A[][], int B[][]) {
		
		int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
	}
	
	/** 
	 * Method to subtract matrix B from A
	 * @param A (first matrix)
	 * @param B (second matrix to be subtracted)
	 * @return updated solution matrix C
	 */
	static int[][] strassenSub(int A[][], int B[][]){
		
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
	}
	
	/**
	 * Method to  recursively multiply matrices A and B
	 * @param A (first matrix to be multiplied)
	 * @param B (second matrix to be multiplied)
	 * @return Solution matrix C equal to A * B
	 */
	static int[][] strassenMultiply(int A[][], int B[][]) {
		
		// Size of A
		int n = A.length;
		
		// Create resulting matrix C
		int [][] C = new int[n][n];
		
		// Base Case, update matrix C
		if (n == 1)
            C[0][0] = A[0][0] * B[0][0];
        else 
        {   // Divide Matrix
			int[][] a11 = new int[n/2][n/2];
			int[][] a12 = new int[n/2][n/2];
			int[][] a21 = new int[n/2][n/2];
			int[][] a22 = new int[n/2][n/2];
			int[][] b11 = new int[n/2][n/2];
			int[][] b12 = new int[n/2][n/2];
			int[][] b21 = new int[n/2][n/2];
			int[][] b22 = new int[n/2][n/2];
			
			// Divide A into 4 halves
			strassenDivide(A, a11, 0, 0);
			strassenDivide(A, a12, 0, n/2);
			strassenDivide(A, a21, n/2, 0);
			strassenDivide(A, a22, n/2, n/2);

			// Divide B into 4 halves
			strassenDivide(B, b11, 0, 0);
			strassenDivide(B, b12, 0, n/2);
			strassenDivide(B, b21, n/2, 0);
			strassenDivide(B, b22, n/2, n/2);
			
			// Creating variables for strassen operations:
			// P = (a11 + a22)(b11 + b22)
			// Q = (a21 + a22)b11
			// R = a11(b12 – b22)
			// S = a22(b21 – b11)
			// T = (a11 + a12)b22
			// U = (a21 – a11)(b11 + b12)
			// V = (a12 – a22)(b21 + b22) 

			
			int[][] p = strassenMultiply(strassenAdd(a11, a22), strassenAdd(b11, b22));
			int[][] q = strassenMultiply(strassenAdd(a21, a22), b11);
			int[][] r = strassenMultiply(a11, strassenSub(b12, b22));
			int[][] s =	strassenMultiply(a22, strassenSub(b21, b11));
			int[][] t = strassenMultiply(strassenAdd(a11, a12),b22);
			int[][] u = strassenMultiply(strassenSub(a21, a11), strassenAdd(b11, b12));
			int[][] v = strassenMultiply(strassenSub(a12, a22), strassenAdd(b21, b22));
			
			// Solving for each element of resulting C:
			// c11 = P + S – T + V
			// c12 = R + T
			// c21 = Q + S
			// c22 = P + R – Q + U

			int[][] c11 = strassenAdd(strassenSub(strassenAdd(p, s), t), v);
			int[][] c12 = strassenAdd(r, t);
			int[][] c21 = strassenAdd(q, s);
			int[][] c22 = strassenAdd(strassenSub(strassenAdd(p, r), q), u);
			
			// Merge the four halves into resulting C:
			strassenMerge(c11, C, 0, 0);
			strassenMerge(c12, C, 0, n/2);
			strassenMerge(c21, C, n/2, 0);
			strassenMerge(c22, C, n/2, n/2);
			
		}
		// Return resulting C Matrix:
		return C;
	}
	
	/** Main driver code
	 *  Tests strassenMult from sizes n = 1 to n = 12 and records the data
	 *  to determine time complexity of the algorithm
	 * @param args
	 */
	public static void main(String args[]) {

		// Print out one run to ensure it is working
		int test1[][] = {{1, 2},
						{3, 4}};
		int test2[][] = {{1, 0},
						{0, 1}};
		System.out.println("Matrix A:"); 
		printMatrix(test1, test1.length);
		System.out.println("Matrix B:");
		printMatrix(test2, test2.length);
		int test3[][] = strassenMultiply(test1, test2);
		System.out.println("Resulting Matrix C:");
		printMatrix(test3, test3.length);
	
		// For data collection
		int[] sizes = new int[10];
		
		// Fill sizes array with powers of 2
		for(int i = 0; i < 10; i++) {
			int n = (int)Math.pow(2,i);
			sizes[i] = n;
		}
		
		    // Loop to test algorithm for different sizes of n that are powers of 2
		for (int i = 0; i < sizes.length; i++) {
	        
			int n = sizes[i];
			
			int A[][] = generateMatrix(n);
		
			int B[][] = generateMatrix(n);
			
			// To measure the elapsed time for testing purposes
	        long startTime = System.currentTimeMillis();
			
			strassenMultiply(A, B);
			
			// To measure the elapsed time for testing purposes
			long endTime = System.currentTimeMillis();
			long timeElapsed = endTime - startTime;
			// Creates/appends (n, timeElapsed) to csv file for documentation
			try {
				// Data to be written to file
				String data = n + ", " + timeElapsed + "\n";
				
				// File that data should be written to
				File dataFile = new File("StrassenTimes.csv");
				
				// Check if file exists, if not make it, if yes then append to it 
				if (!dataFile.exists()) {
					dataFile.createNewFile();
					FileWriter fw = new FileWriter(dataFile.getName(), true);
					fw.write(data);
					fw.close();
				}
				else {
					String file = dataFile.getAbsolutePath();
					FileWriter fw = new FileWriter(file, true);
					fw.write(data);
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Output for testing purposes
			//System.out.println("Matrix A is ");
			//printMatrix(A, A.length);
			//System.out.println("Matrix B is ");
			//printMatrix(B, B.length);
			//System.out.println("Matrix C is ");
			//printMatrix(C, C.length);
		}		
		// Indicate when all testing is done.
		System.out.print("Done");		
	}
}
