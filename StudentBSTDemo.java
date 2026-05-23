class StudentNode {
    int studentID;
    String studentName;
    double cgpa;
    StudentNode left, right;

    StudentNode(int id, String name, double cgpa) {
        this.studentID = id;
        this.studentName = name;
        this.cgpa = cgpa;
        this.left = this.right = null;
    }
}

class StudentRecordBST {

    StudentNode root;

    // ── INSERT ──────────────────────────────────────────────
    StudentNode insert(StudentNode root, int id, String name, double cgpa) {
        if (root == null)
            return new StudentNode(id, name, cgpa);

        if (id < root.studentID)
            root.left = insert(root.left, id, name, cgpa);
        else if (id > root.studentID)
            root.right = insert(root.right, id, name, cgpa);
        else
            System.out.println("Student ID " + id + " already exists.");

        return root;
    }

    // ── SEARCH ──────────────────────────────────────────────
    StudentNode search(StudentNode root, int id) {
        if (root == null || root.studentID == id)
            return root;

        if (id < root.studentID)
            return search(root.left, id);
        else
            return search(root.right, id);
    }

    // ── FIND MINIMUM (used in delete) ────────────────────────
    StudentNode findMin(StudentNode root) {
        while (root.left != null)
            root = root.left;
        return root;
    }

    // ── DELETE ──────────────────────────────────────────────
    StudentNode delete(StudentNode root, int id) {
        if (root == null) {
            System.out.println("Student ID " + id + " not found.");
            return null;
        }

        if (id < root.studentID) {
            root.left = delete(root.left, id);
        } else if (id > root.studentID) {
            root.right = delete(root.right, id);
        } else {
            // Case 1: Leaf node
            if (root.left == null && root.right == null)
                return null;

            // Case 2: One child
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Case 3: Two children — replace with in-order successor
            StudentNode successor = findMin(root.right);
            root.studentID   = successor.studentID;
            root.studentName = successor.studentName;
            root.cgpa        = successor.cgpa;
            root.right       = delete(root.right, successor.studentID);
        }

        return root;
    }

    // ── IN-ORDER TRAVERSAL (sorted listing) ─────────────────
    void inOrder(StudentNode root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.printf("  ID: %5d | %-20s | CGPA: %.2f%n",
                root.studentID, root.studentName, root.cgpa);
        inOrder(root.right);
    }

    // ── DISPLAY ALL STUDENTS ─────────────────────────────────
    void printAllStudents() {
        System.out.println("Student Directory (Sorted by ID):");
        System.out.println("  -----------------------------------------------");
        inOrder(root);
        System.out.println("  -----------------------------------------------");
    }
}

public class StudentBSTDemo {
    public static void main(String[] args) {

        StudentRecordBST srs = new StudentRecordBST();

        // Insert student records
        System.out.println("=== Admitting Students ===");
        srs.root = srs.insert(srs.root, 1050, "Arjun Sharma",   8.75);
        srs.root = srs.insert(srs.root, 300,  "Priya Mehta",    9.10);
        srs.root = srs.insert(srs.root, 1800, "Ravi Kumar",     7.50);
        srs.root = srs.insert(srs.root, 750,  "Sneha Reddy",    8.20);
        srs.root = srs.insert(srs.root, 150,  "Anil Verma",     6.95);
        srs.root = srs.insert(srs.root, 900,  "Divya Nair",     9.40);
        srs.root = srs.insert(srs.root, 2100, "Kiran Patel",    7.80);

        srs.printAllStudents();

        // Search
        System.out.println("=== Searching for Student ID 750 ===");
        StudentNode result = srs.search(srs.root, 750);
        if (result != null)
            System.out.printf("   Found: ID=%d, Name=%s, CGPA=%.2f%n",
                    result.studentID, result.studentName, result.cgpa);
        else
            System.out.println("   Student not found.");

        System.out.println("=== Searching for Student ID 999 ===");
        result = srs.search(srs.root, 999);
        if (result != null)
            System.out.printf("   Found: %s%n", result.studentName);
        else
            System.out.println("   Student not found.");

        // Delete
        System.out.println("=== Removing Student ID 150 (leaf node) ===");
        srs.root = srs.delete(srs.root, 150);

        System.out.println("=== Removing Student ID 1050 (two children) ===");
        srs.root = srs.delete(srs.root, 1050);

        srs.printAllStudents();
    }
}
