/**
 * Segment_Tree - Range Maximum Query (RMQ)
**/


import java.util.Arrays;
import java.util.Scanner;


class SegmentTree {
    private final int N;
    private final int[] userInputs;
    private final int[] tree;

    SegmentTree(int[] inputs) {
        N = inputs.length;
        userInputs = inputs;
        tree = new int[(N << 2)];
        build(0, 0, N - 1);
    }
    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = userInputs[start];
            return;
        }

        int mid = start + ((end - start) >> 1);
        int left = (node << 1) + 1;
        int right = (node << 1) + 2;

        build(left, start, mid);
        build(right, mid + 1, end);
        tree[node] = Integer.max(tree[left], tree[right]);
    }
    public int query(int left, int right) {
        return queryMax(0, 0, N - 1, left, right);
    }
    private int queryMax(int node, int start, int end, int left, int right) {
        // No overlap
        if (right < start || left > end) return Integer.MIN_VALUE;

        // Total overlap
        if (left <= start && right >= end) return tree[node];

        // Partial overlap
        int mid = start + ((end - start) >> 1);
        int leftSum = queryMax((node << 1) + 1, start, mid, left, right);
        int rightSum = queryMax((node << 1) + 2, mid + 1, end, left, right);
        return Integer.max(leftSum, rightSum);
    }
    public String toString() {
        return "ArrayLen: " + N + "\nArray: " + Arrays.toString(userInputs) + "\nTree: " + Arrays.toString(tree);
    }
}

public class Segment_Tree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // int[] piles = {1, 2, 3, 8, 16, 6, 10, 50};
        System.out.print("Enter the array elements: ");
        int[] piles = Arrays.stream(sc.nextLine().trim().split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
        SegmentTree sg = new SegmentTree(piles);
        System.out.println(sg);

        boolean keepAsking = true;
        do {
            System.out.print("Please enter the range (space separated two values) OR enter 'end' or 'exit' to stop: ");
            String userInput = sc.nextLine().strip().toLowerCase();
            if (userInput.contains("end") || userInput.contains("exit")) {
                keepAsking = false;
                System.out.println("Closing program as per the instruction.");
            } else {
                int[] range = Arrays.stream(userInput.split("\\s+"))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                int maxValInRange = sg.query(range[0], range[1]);
                if (maxValInRange == Integer.MIN_VALUE)
                    System.err.println("Wrong query range: " + Arrays.toString(range));
                else
                    System.err.println("Max value in range" + Arrays.toString(range) + ": " + maxValInRange);
            }
        } while (keepAsking);
        sc.close();

        // for (int i = 0; i < piles.length; i++) {
        //     int leftRange = Integer.max(0, i - 1), rightRange = Integer.min(piles.length - 1, i + 2);
        //     int maxValInRange = sg.query(leftRange, rightRange);
        //     System.err.println("Max value in range [" + leftRange + ", " + rightRange + "]: " + maxValInRange);
        // }

    }
}


