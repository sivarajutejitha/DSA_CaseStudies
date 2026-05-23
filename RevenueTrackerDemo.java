// ============================================================
// Case Study 2: E-Commerce Revenue Tracker using Fenwick Tree
// CO2 - Fenwick Tree (Binary Indexed Tree)
// ============================================================

class FenwickTree {

    int[] tree;
    int n;

    FenwickTree(int n) {
        this.n = n;
        this.tree = new int[n + 1]; // 1-indexed
    }

    // ── POINT UPDATE ────────────────────────────────────────
    // Add 'delta' to position 'i'
    void update(int i, int delta) {
        while (i <= n) {
            tree[i] += delta;
            i += (i & -i); // move to next responsible node
        }
    }

    // ── PREFIX SUM QUERY ────────────────────────────────────
    // Sum from index 1 to i
    int prefixSum(int i) {
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= (i & -i); // move to parent node
        }
        return sum;
    }

    // ── RANGE SUM QUERY ─────────────────────────────────────
    // Sum from index l to r
    int rangeSum(int l, int r) {
        return prefixSum(r) - prefixSum(l - 1);
    }
}

public class RevenueTrackerDemo {

    public static void main(String[] args) {

        int[] dailyRevenue = {0, 5000, 3200, 4700, 6100, 2800, 5500, 4300, 7200};
        // index 0 unused; days 1 to 8

        int days = 8;
        FenwickTree ft = new FenwickTree(days);

        // Build Fenwick Tree
        System.out.println("=== Building Fenwick Tree with Daily Revenues ===");
        for (int i = 1; i <= days; i++) {
            ft.update(i, dailyRevenue[i]);
            System.out.printf("  Day %d: Rs. %d%n", i, dailyRevenue[i]);
        }

        // Prefix Sum Queries
        System.out.println("\n=== Prefix Sum Queries ===");
        System.out.printf("  Total revenue from Day 1 to Day 4 : Rs. %d%n",
                ft.prefixSum(4));
        System.out.printf("  Total revenue from Day 1 to Day 8 : Rs. %d%n",
                ft.prefixSum(8));

        // Range Sum Queries
        System.out.println("\n=== Range Sum Queries ===");
        System.out.printf("  Revenue from Day 3 to Day 6 : Rs. %d%n",
                ft.rangeSum(3, 6));
        System.out.printf("  Revenue from Day 5 to Day 8 : Rs. %d%n",
                ft.rangeSum(5, 8));

        // Point Update — correct Day 3 revenue
        int oldVal = dailyRevenue[3];
        int newVal = 6200;
        System.out.printf("%n=== Updating Day 3 Revenue: Rs. %d → Rs. %d ===%n",
                oldVal, newVal);
        ft.update(3, newVal - oldVal); // add the difference

        // Re-query after update
        System.out.println("\n=== After Update ===");
        System.out.printf("  Total revenue from Day 1 to Day 4 : Rs. %d%n",
                ft.prefixSum(4));
        System.out.printf("  Revenue from Day 3 to Day 6 : Rs. %d%n",
                ft.rangeSum(3, 6));
    }
}
