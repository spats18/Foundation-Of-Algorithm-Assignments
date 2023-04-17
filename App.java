import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("-----------------------------------");
        System.out.println(" Assignment 5: Dynamic Programming");
        System.out.println("-----------------------------------");

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of Skis: ");
        int m = sc.nextInt();

        float[] skis = new float[m + 1];
        System.out.println("Enter the heights of Skis");
        // ski i is stored at i position
        for (int i = 1; i <= m; i++)
            skis[i] = sc.nextFloat();

        System.out.print("Enter the number of Skiers: ");
        int n = sc.nextInt();

        float[] skiers = new float[n + 1];
        // skier i is stored at i position
        System.out.println("Enter the heights of Skiers");
        for (int i = 1; i <= n; i++)
            skiers[i] = sc.nextFloat();

        sc.close();

        // PART 1: Algorithm to compute c[i, j] that contains the value indicating the
        // optimal solution
        // Introducing another 2D array (p) containing the positions where the optimal
        // values occur.
        float[][] c = new float[n + 1][m + 1];
        char[][] p = new char[n + 1][m + 1];

        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= m; j++) {
                // CASE 1: (i = 0 or j = 0) and (i <= j)
                if ((i == 0 || j == 0) && i <= j)
                    c[i][j] = 0;

                // CASE 3: i > j >= 0
                else if (i > j)
                    c[i][j] = Integer.MAX_VALUE;

                // CASE 2: 1 <= i <= j
                else {
                    float diag = c[i - 1][j - 1] + Math.abs(skiers[i] - skis[j]);
                    float prev = c[i][j - 1];
                    if (diag <= prev) {
                        c[i][j] = diag;
                        p[i][j] = '+';
                        // positive (+) value to indicate we have taken the diagonal value
                    } else {
                        c[i][j] = prev;
                        p[i][j] = '-';
                        // negative (-) value to indicate we have taken the previous column value
                    }
                }
            }

        System.out.println("-----------------------------------");
        System.out.println("Optimal cost for matching: " + String.format("%.2f",c[n][m]));
        System.out.println("-----------------------------------");
        System.out.println("Assignments of Skier and Ski are as follows");
        // PART 2: Algorithm that constructs an optimal solution
        constructOptimalSolution(c, p, n, m, skis, skiers);
        System.out.println("-----------------------------------");
    }

    private static void constructOptimalSolution(float[][] c, char[][] p, int i, int j, float[] skis, float[] skiers) {
        if (i == 0 || j == 0)
            return;
        if (p[i][j] == '+') {
            constructOptimalSolution(c, p, i - 1, j - 1, skis, skiers);
            System.out.println("Skier " + i + " (" + skiers[i] + ") -> Ski " + j + " (" + skis[j] + ")");
        } else if (p[i][j] == '-') {
            constructOptimalSolution(c, p, i, j - 1, skis, skiers);
        }
        return;
    }
}