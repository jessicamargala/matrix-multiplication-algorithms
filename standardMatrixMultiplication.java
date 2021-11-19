/** A class that implements the Classical Matrix Multiplication algorithm. 
 * @author Jessica Margala
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class standardMatrixMultiplication {
	
	/**
	 * Method to generate Matrix filled with random values from 0 - 10 (for testing)
	 * @param n (desired size of matrix)
	 * @return new filled matrix of size nxn
	 */
	static int[][] generateMatrix(int n) {
		
		Random number = new Random();
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = number.nextInt(10);
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
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
	}
	
	/**
	 * Method to multiply two matrices A and B, given the size n of the matrices
	 * @param A (first matrix)
	 * @param B (second matrix)
	 * @param n (the size of the matrix)
	 * @return new array C, result of A * B
	 */
	static int[][] matrixmult(int A[][], int B[][], int n) {
		// Generate new array C for resulting matrix
		int [][] C = new int[n][n];
		
		// Indices
		int i;
		int j;
		int k;
		
		for(i = 0; i < n; i++) 
			for(j = 0; j < n; j++) {
				C[i][j] = 0;	// Initialize resulting matrix 
				for(k = 0; k < n; k++)
					C[i][j] += A[i][k] * B[k][j];
			}
		return C;
	}
	
	/** 
	 *  Main driver code
	 *  Tests matrixmult from sizes n = 1 to n = 12 and records the data
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
		int test3[][] = matrixmult(test1, test2, test1.length);
		System.out.println("Resulting Matrix C:");
		printMatrix(test3, test3.length);
		
		// For data collection
		int[] sizes = new int[12];
		
		// Fill sizes array with powers of 2
		for(int i = 0; i < 12; i++) {
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
			
			matrixmult(A, B, A.length);
			
			// To measure the elapsed time for testing purposes
			long endTime = System.currentTimeMillis();
			long timeElapsed = endTime - startTime;
			// Creates/appends (n, timeElapsed) to csv file for documentation
			try {
				// Data to be written to file
				String data = n + ", " + timeElapsed + "\n";
				
				// File that data should be written to
				File dataFile = new File("StandardTimes.csv");
				
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