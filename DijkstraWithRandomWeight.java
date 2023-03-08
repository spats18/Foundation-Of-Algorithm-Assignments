import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class DijkstraWithRandomWeight {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices");
        int V = sc.nextInt();
        HashMap<Integer, Integer>[] graph = new HashMap[V];
        System.out.println("Enter the number of edges");
        int E = sc.nextInt();
        System.out.println("Enter the beginning point then the end point and then the time needed to travel");
        while(E>0){
            int src = sc.nextInt()-1;
            int dest = sc.nextInt()-1;
            int cost = sc.nextInt();
            if(graph[src]==null)
                graph[src] = new HashMap<>();
            graph[src].put(dest, cost);
            E--;
        }

        System.out.println("Enter the starting point and destination");
        int src = sc.nextInt()-1;
        int dest = sc.nextInt()-1;
        HashMap<Integer, Route> result = new HashMap<>();
        int[] distance = new int[V];
        getShortestPath(graph, result, src, distance);
        System.out.println("Time needed to travel to destination: "+ result.get(dest).cost + "\nPath taken would be "+ result.get(dest).path);
        System.out.println("All nodes shortest time as below");
        for(int node : result.keySet()){
            System.out.println((node+1)+": time: "+ result.get(node).cost + "\tPath: "+ result.get(node).path);
        }
    }
    private static void getShortestPath(HashMap<Integer, Integer>[] graph, HashMap<Integer, Route> result, int src, int[] distance) {
        PriorityQueue<Route> heap = new PriorityQueue<>((a,b)->(a.cost - b.cost));
        for(int i=0; i<graph.length; i++){
            if(i==src)
                heap.add(new Route("", 0, src));
            else{
                distance[i] = Integer.MAX_VALUE;
                heap.add(new Route("", Integer.MAX_VALUE, i));
            }
        }
        while(result.size()!=graph.length){
            Route minRoute = heap.poll();
            if(result.containsKey(minRoute.node))
                continue;
            minRoute.path += (minRoute.node+1);
            result.put(minRoute.node, minRoute);
            HashMap<Integer, Integer> current = graph[minRoute.node];
            if(current == null)
                continue;
            for(int adj : current.keySet()){
                int expected = minRoute.cost + current.get(adj);
                int delayed = getDelay(minRoute.node, adj, expected);
                int earlier = distance[adj];
                if(earlier > delayed){
                    distance[adj] = delayed;
                    heap.add(new Route(minRoute.path, delayed, adj));
                }
                graph[minRoute.node].put(adj, delayed - minRoute.cost);
                System.out.println("The distance between nodes "+(minRoute.node+1) + " and "+ (adj+1) + 
                " has been changed and updated considering the weather from " + 
                (expected - minRoute.cost) +" to "+ (delayed - minRoute.cost));
            }
            printGraph(graph);
            System.out.println("Time in which nodes can be reached");
            for(int i=0; i<distance.length; i++){
                System.out.print((i+1) +":");
                if(distance[i]!=Integer.MAX_VALUE) 
                    System.out.print(distance[i]+ "\t");
                else
                    System.out.print("infinite\t");
            }
            System.out.println();
        }
    }
    public static int getDelay(int src, int dest, int time){
        Random r = new Random();
        int low = time;
        int high = time + 20;
        int newArrival = r.nextInt(high-low) + low;
        return newArrival;
    }
    public static void printGraph(HashMap<Integer, Integer>[] graph){
        System.out.println("New graph edges will be as follows");
        for(int i = 0; i<graph.length; i++){
            if(graph[i]!= null)
                for(int adj: graph[i].keySet()){
                    System.out.println("Edge: "+(i+1)+ "-"+(adj+1)+" (" + graph[i].get(adj)+ ") ");
                }
        }
    }
}
class Route {
    String path;
    int cost;
    int node;
    Route(String p, int c, int n){
        this.path = p;
        this.cost = c;
        this.node = n;
    }
}
