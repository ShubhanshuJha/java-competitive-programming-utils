/**
 * Disjoint Set Union (DSU) / Union-Find
 *
 * Optimizations:
 * 1. Path Compression
 * 2. Union by Size
 *
 * Time Complexity:
 * find()  -> O(alpha(N)) amortized
 * union() -> O(alpha(N)) amortized
 *
 * Space Complexity: O(N)
 */
public class Disjoint_Set {

    private final int[] parent;
    private final int[] size;
    private int components;

    /**
     * Creates N disjoint sets: {0}, {1}, ..., {N - 1}
     */
    public Disjoint_Set(int n) {
        parent = new int[n];
        size = new int[n];
        components = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Finds the representative (root) of the set containing x.
     * Uses path compression.
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    /**
     * Merges the sets containing a and b using union by size.
     *
     * @return true if two different sets were merged,
     *         false if they were already connected.
     */
    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        // Attach the smaller tree to the larger tree.
        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];
        components--;

        return true;
    }

    /**
     * Checks whether a and b belong to the same component.
     */
    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    /**
     * Returns the number of elements in the component containing x.
     */
    public int size(int x) {
        return size[find(x)];
    }

    /**
     * Returns the current number of disjoint components.
     */
    public int components() {
        return components;
    }

    /**
     * Example usage.
     */
    public static void main(String[] args) {
        Disjoint_Set dsu = new Disjoint_Set(7);

        dsu.union(1, 2);
        dsu.union(2, 3);

        dsu.union(4, 5);
        dsu.union(5, 6);

        System.out.println(dsu.connected(1, 3)); // true
        System.out.println(dsu.connected(1, 4)); // false

        System.out.println(dsu.size(1));         // 3
        System.out.println(dsu.size(4));         // 3
        System.out.println(dsu.components());    // 3

        dsu.union(3, 4);

        System.out.println(dsu.connected(1, 6)); // true
        System.out.println(dsu.size(1));         // 6
        System.out.println(dsu.components());    // 2
    }
}
