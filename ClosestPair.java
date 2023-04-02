import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ClosestPair {
    
    public static void main(String args[]){
        System.out.println("Closest Pair of Points Algorithm");
        System.out.println("--------------------------------");
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of points");
        int n = sc.nextInt();
        int[][] points = new int[n][2];

        System.out.println("Enter x and y coordinate points");
        for(int i=0; i<n; i++){
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }
        Arrays.sort(points, (a,b)->a[0]-b[0]);

        //calling the algorithm
        double dist = closestPair(points, 0, n-1);
        sc.close();
        System.out.println("Distance between closest pair of points is " + String.format("%.2f", dist));
    }

    public static double closestPair(int[][] points, int si, int ei){
        if(ei - si <= 0)
            return Double.MAX_VALUE;

        if(ei-si == 1)
            return calEucDist(points[si], points[ei]);

        int mid = si + (ei - si)/2;

        double diff1 = closestPair(points, si, mid);
        double diff2 = closestPair(points, mid, ei);

        double diff = Math.min(diff1, diff2);

        //making a set of points with distance diff from separation line
        ArrayList<int[]> setOfY = new ArrayList<>();
        for(int i=si; i<=ei; i++){
            if(Math.abs(points[i][0] - points[mid][0]) <= diff)
                setOfY.add(points[i]); 
        }

        //Sorting setOfY
        Collections.sort(setOfY, (a,b)->(a[1]-b[1]));

        //Scanning points comparing the distance between each point and next x-1 neighbors
        for(int i=0; i<setOfY.size(); i++){
            for(int j = 1; j<=7 && i+j < setOfY.size() ; j++){
                double dist = calEucDist(setOfY.get(i), setOfY.get(i+j));
                diff = Math.min(diff, dist);
            }
        }
        return diff;
    }

    public static double calEucDist(int[] p1, int[] p2){
        int distX = Math.abs(p1[0]-p2[0]);
        int distY = Math.abs(p1[1]-p2[1]);

        double dist = Math.sqrt(distX * distX + distY * distY);
        return dist;
    }
}



// setOfY.add(new points[mid]);
        // int midX = points[mid][0];
        // int idx = mid - 1;
        // while(idx >= si){
        //     int distX = points[idx][0];
        //     if(Math.abs(distX-midX) > diff)
        //         break;
        //     setOfY.add(new points[idx]); 
        //     idx--;
        // }
        // idx = mid + 1;
        // while(idx <= ei){
        //     int distX = points[idx][0];
        //     if(Math.abs(distX-midX) > diff)
        //         break;
        //     setOfY.add(new points[idx]); 
        //     idx++;
        // }
