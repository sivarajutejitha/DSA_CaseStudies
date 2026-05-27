public class EmployeeHeapSort {
    // ── HEAPIFY ─────────────────────────────────────────────
    // Maintain max-heap property at index i for heap of size n
    static void heapify(int[] salary, String[] names, int n, int i) {
        int largest = i;        // assume root is largest
        int left    = 2 * i + 1;
        int right   = 2 * i + 2;
        // Check if left child is larger
        if (left < n && salary[left] > salary[largest])
            largest = left;
        // Check if right child is larger
        if (right < n && salary[right] > salary[largest])
            largest = right;
        // If largest is not root, swap and continue heapifying
        if (largest != i) {
            // Swap salaries
            int tempSal      = salary[i];
            salary[i]        = salary[largest];
            salary[largest]  = tempSal;
            // Swap names to keep records aligned
            String tempName  = names[i];
            names[i]         = names[largest];
            names[largest]   = tempName;
            heapify(salary, names, n, largest);
        }
    }
    // ── HEAP SORT ───────────────────────────────────────────
    static void heapSort(int[] salary, String[] names, int n) {
        // Step 1: Build Max-Heap
        System.out.println("\n=== Building Max-Heap ===");
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(salary, names, n, i);
            System.out.print("  Heapify at index " + i + " : [");
            for (int k = 0; k < n; k++)
                System.out.print(salary[k] + (k < n - 1 ? ", " : ""));
            System.out.println("]");
        }
        // Step 2: Extract elements from heap one by one
        System.out.println("\n=== Sorting in Progress ===");
        for (int i = n - 1; i > 0; i--) {
            // Move current root (max) to end
            int tempSal   = salary[0];
            salary[0]     = salary[i];
            salary[i]     = tempSal;
            String tempName = names[0];
            names[0]        = names[i];
            names[i]        = tempName;
            System.out.printf("  Step %d : Extracted Rs. %d -> Heap Size = %d%n",
                    n - i, tempSal, i);
            // Heapify the reduced heap
            heapify(salary, names, i, 0);
        }
    }
    // ── MAIN ────────────────────────────────────────────────
    public static void main(String[] args) {
        String[] names = {
            "Ravi Kumar", "Sneha Reddy", "Arjun Sharma", "Priya Mehta",
            "Kiran Patel", "Divya Nair",  "Anil Verma",   "Pooja Singh"
        };
        int[] salary = {72000, 45000, 98000, 61000, 83000, 54000, 39000, 76000};
        int n = salary.length;
        // Print before sorting
        System.out.println("=== Employee Records (Before Sorting) ===");
        for (int i = 0; i < n; i++)
            System.out.printf("  Emp %d : %-16s | Salary: Rs. %d%n",
                    i + 1, names[i], salary[i]);
        // Perform Heap Sort
        heapSort(salary, names, n);
        // Print after sorting
        System.out.println("\n=== Employee Salary Ranking (Ascending Order) ===");
        for (int i = 0; i < n; i++)
            System.out.printf("  Rank %d : %-16s | Salary: Rs. %d%n",
                    i + 1, names[i], salary[i]);
    }
}