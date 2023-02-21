package assignment1;

import java.util.Random;
import java.util.Scanner;

public class SortingAlgorithm {
	public static void main(String[] args) {
		System.out.println("Assignment 1 : Q5");
		System.out.println("Enter the size of Array: ");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] A = new int[n];
		int[] B = new int[n];
		System.out.println("Generating numbers for array A");
		for (int i = 0; i < n; i++) {
			Random random = new Random();
			int rnd = (int) (random.nextInt(1000));
			A[i] = rnd;
			System.out.print(A[i] + " ");
		}
		long start = System.nanoTime();
		System.out.println("\nCalling Sorting Algorithm");
		for (int i = n - 1; i >= 0; i--) {
			int maxIdx = 0;
			// find largest element
			for (int j = 1; j <= i; j++)
				if (A[j] > A[maxIdx])
					maxIdx = j;
			// swap for A
			int temp = A[maxIdx];
			A[maxIdx] = A[i];
			A[i] = temp;
			// Insert the chosen element to Array B
			B[i] = A[i];
		}
		long end = System.nanoTime();
		System.out.println("Elapsed Time in nano seconds: " + (end - start));
		System.out.print("Array B after Sorting: ");
		for (int i = 0; i < n; i++)
			System.out.print(B[i] + " ");
	}
}
