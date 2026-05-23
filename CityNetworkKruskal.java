// ============================================================
// Case Study 3: City Network Infrastructure Design
// CO3 - Kruskal's Algorithm (Minimum Spanning Tree)
// ============================================================

import java.util.Arrays;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src    = src;
        this.dest   = dest;
        this.weight = weight;
    }

    // Sort edges by weight (ascending)
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class UnionFind {
    int[] parent, rank;

    UnionFind(int n) {
        parent = new int[n];
        rank   = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i; // each node is its own parent initially
    }

    // ── FIND with Path Compression ───────────────────────────
    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    // ── UNION by Rank ────────────────────────────────────────
    boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY)
            return false; // already in same component → cycle

        if (rank[rootX] < rank[rootY])
            parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY])
            parent[rootY] = rootX;
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}

public class CityNetworkKruskal {

    public static void main(String[] args) {

        int V = 6; // Number of city zones
        int E = 10; // Number of possible cable routes

        Edge[] edges = {
            new Edge(0, 1, 4),
            new Edge(0, 2, 3),
            new Edge(1, 2, 1),
            new Edge(1, 3, 2),
            new Edge(2, 3, 4),
            new Edge(2, 4, 5),
            new Edge(3, 4, 7),
            new Edge(3, 5, 6),
            new Edge(4, 5, 8),
            new Edge(0, 5, 9)
        };

        // Print all possible routes
        System.out.println("=== City Zones and Possible Cable Routes ===");
        for (Edge e : edges)
            System.out.printf("  Zone %d -- Zone %d  :  %d km%n",
                    e.src, e.dest, e.weight);

        // Sort edges by weight
        Arrays.sort(edges);

        UnionFind uf = new UnionFind(V);
        Edge[] mst   = new Edge[V - 1];
        int mstCost  = 0;
        int edgeCount = 0;

        System.out.println("=== Applying Kruskal's Algorithm ===");

        for (Edge e : edges) {
            if (edgeCount == V - 1) break;

            if (uf.union(e.src, e.dest)) {
                // Edge accepted — no cycle formed
                mst[edgeCount++] = e;
                mstCost += e.weight;
                System.out.printf("   Selected : Zone %d -- Zone %d  :  %d km%n",
                        e.src, e.dest, e.weight);
            } else {
                // Edge rejected — would form a cycle
                System.out.printf("   Skipped  : Zone %d -- Zone %d  :  %d km" +
                        "  (would form cycle)%n",
                        e.src, e.dest, e.weight);
            }
        }

        // Final MST Result
        System.out.println("=== Minimum Spanning Tree Result ===");
        System.out.printf("  Total Zones    : %d%n", V);
        System.out.printf("  Edges Selected : %d%n", edgeCount);
        System.out.printf("  Minimum Cable Length Required : %d km%n", mstCost);
    }
}