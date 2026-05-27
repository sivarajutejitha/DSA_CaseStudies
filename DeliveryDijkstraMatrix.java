// ============================================================
// Case Study 4: Logistics Shortest Path using Dijkstra's
// CO4 - Dijkstra's Algorithm (Adjacency Matrix Version)
// ============================================================

import java.util.*;

public class DeliveryDijkstraMatrix {

    static final int INF = Integer.MAX_VALUE;
    static int V = 6;

    // ── FIND MINIMUM DISTANCE NODE ──────────────────────────
    static int minDistance(int[] dist, boolean[] visited) {
        int min = INF, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // ── PATH RECONSTRUCTION ─────────────────────────────────
    static String getPath(int[] parent, int dest) {
        if (parent[dest] == -1)
            return String.valueOf(dest);
        return getPath(parent, parent[dest]) + " -> " + dest;
    }

    // ── DIJKSTRA'S ALGORITHM ────────────────────────────────
    static void dijkstra(int[][] graph, int src) {

        int[] dist    = new int[V];
        int[] parent  = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        System.out.println("\n=== Applying Dijkstra's Algorithm from Warehouse "
                + src + " ===");

        for (int count = 0; count < V - 1; count++) {

            // Pick unvisited node with minimum distance
            int u = minDistance(dist, visited);
            visited[u] = true;

            System.out.printf("  Processing : Location %d  (dist = %d)%n",
                    u, dist[u]);

            // Relax all neighbors of u
            for (int v = 0; v < V; v++) {
                if (!visited[v]
                        && graph[u][v] != 0
                        && dist[u] != INF
                        && dist[u] + graph[u][v] < dist[v]) {

                    dist[v]   = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        // Print results
        System.out.println("\n=== Shortest Delivery Routes from Warehouse "
                + src + " ===");
        for (int i = 0; i < V; i++) {
            if (i == src) continue;
            if (dist[i] == INF)
                System.out.printf("  To Location %d : Unreachable%n", i);
            else
                System.out.printf(
                        "  To Location %d : Distance = %2d km  | Path : %s%n",
                        i, dist[i], getPath(parent, i));
        }
    }

    // ── MAIN ────────────────────────────────────────────────
    public static void main(String[] args) {

        // Adjacency Matrix (0 means no direct connection)
        int[][] graph = {
            //  0   1   2   3   4   5
            {   0,  4,  1,  0,  0,  0  },  // Warehouse 0
            {   0,  0,  0,  1,  6,  0  },  // Location 1
            {   0,  2,  0,  5,  0,  0  },  // Location 2
            {   0,  0,  0,  0,  3,  8  },  // Location 3
            {   0,  0,  0,  0,  0,  2  },  // Location 4
            {   0,  0,  0,  0,  0,  0  }   // Location 5
        };

        // Print network
        System.out.println("=== Delivery Network (Adjacency Matrix) ===");
        System.out.println("  Warehouse 0 -> Location 1  :  4 km");
        System.out.println("  Warehouse 0 -> Location 2  :  1 km");
        System.out.println("  Location 1  -> Location 3  :  1 km");
        System.out.println("  Location 2  -> Location 1  :  2 km");
        System.out.println("  Location 2  -> Location 3  :  5 km");
        System.out.println("  Location 3  -> Location 4  :  3 km");
        System.out.println("  Location 1  -> Location 4  :  6 km");
        System.out.println("  Location 4  -> Location 5  :  2 km");
        System.out.println("  Location 3  -> Location 5  :  8 km");

        // Run Dijkstra from Warehouse 0
        dijkstra(graph, 0);
    }
}