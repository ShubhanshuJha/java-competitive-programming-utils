import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;


class Solution {
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        char[] str = s.toCharArray();
        int k = queryIndices.length;
        int[] res = new int[k];

        // segments: run-start-index -> run-length, partitioning the whole string
        TreeMap<Integer, Integer> segments = new TreeMap<>();
        // lengthCount: run-length -> how many runs currently have that length
        TreeMap<Integer, Integer> lengthCount = new TreeMap<>();

        int i = 0;
        while (i < n) {
            int j = i;
            while (j + 1 < n && str[j + 1] == str[i]) j++;
            int len = j - i + 1;
            segments.put(i, len);
            lengthCount.merge(len, 1, Integer::sum);
            i = j + 1;
        }

        for (int q = 0; q < k; q++) {
            int idx = queryIndices[q];
            char newChar = queryCharacters.charAt(q);
            char oldChar = str[idx];

            if (newChar != oldChar) {
                int start = segments.floorKey(idx);
                int len = segments.remove(start);
                decrementCount(lengthCount, len);
                int end = start + len - 1;

                if (idx > start) {
                    int leftLen = idx - start;
                    segments.put(start, leftLen);
                    lengthCount.merge(leftLen, 1, Integer::sum);
                }
                if (idx < end) {
                    int rightLen = end - idx;
                    segments.put(idx + 1, rightLen);
                    lengthCount.merge(rightLen, 1, Integer::sum);
                }

                str[idx] = newChar;

                int mergedStart = idx;
                int mergedLen = 1;
                if (idx - 1 >= 0 && str[idx - 1] == newChar) {
                    int leftStart = segments.floorKey(idx - 1);
                    int leftLen = segments.remove(leftStart);
                    decrementCount(lengthCount, leftLen);
                    mergedStart = leftStart;
                    mergedLen += leftLen;
                }
                if (idx + 1 < n && str[idx + 1] == newChar) {
                    int rightStart = segments.floorKey(idx + 1);
                    int rightLen = segments.remove(rightStart);
                    decrementCount(lengthCount, rightLen);
                    mergedLen += rightLen;
                }

                segments.put(mergedStart, mergedLen);
                lengthCount.merge(mergedLen, 1, Integer::sum);
            }

            res[q] = lengthCount.lastKey();
        }

        return res;
    }

    private void decrementCount(TreeMap<Integer, Integer> lengthCount, int len) {
        int c = lengthCount.get(len);
        if (c == 1) lengthCount.remove(len);
        else lengthCount.put(len, c - 1);
    }
}



public class LT_Solution {
    public static void main(String[] args) throws IOException {
        long processStarted = System.currentTimeMillis();
        String inputFileName = "input.txt";
        String outputFileName = "output.txt";
        try (FastIO io = new FastIO(inputFileName, outputFileName)) {
            String str = io.readString();
            String queryChars = io.readString();
            int[] idx = io.readIntArray();
            long inputEnded = System.currentTimeMillis();
            io.write("User input loaded in " + (inputEnded - processStarted) + "ms.", true);

            Solution solution = new Solution();
            var result = solution.longestRepeating(str, queryChars, idx);
            io.write("Result: " + Arrays.toString(result));
            long processEnded = System.currentTimeMillis();
            io.write("Solution loaded in " + (processEnded - inputEnded) + "ms.");
            io.write("Program ran for " + (processEnded - processStarted) + " ms.");
        }
    }
}

