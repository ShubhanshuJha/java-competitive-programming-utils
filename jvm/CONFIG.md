# JVM Configuration for Competitive Programming

Java's default JVM settings are tuned for long-running applications, not for a program that needs to read a huge input file, recurse deeply, and finish in a few seconds. This guide covers the flags that actually matter for contests with large constraints — like **Meta Hacker Cup**, **Google Code Jam-style** rounds, or any judge that lets you run locally with your own JVM flags.

> 📌 Most online judges (Codeforces, LeetCode, AtCoder) don't let you pass custom JVM flags — they run your code with fixed limits. This guide is primarily for contests like **Meta Hacker Cup**, where you download the input and execute your solution **locally**, giving you full control over the JVM.

---

## 🧠 Core Flags You'll Actually Use

| Flag | Purpose | Typical CP Value |
|---|---|---|
| `-Xmx` | Maximum heap size | `1024m` – `4g` (depends on input size) |
| `-Xms` | Initial heap size | Set equal to `-Xmx` to avoid resize pauses |
| `-Xss` | Thread stack size | `16m` – `64m` (for deep recursion) |
| `-XX:+UseSerialGC` | Use the simplest garbage collector | Recommended for short-lived programs |
| `-server` | JIT-compiles more aggressively (default on 64-bit JVMs, but explicit doesn't hurt) | Optional |

---

## 🚀 Compile & Run Commands

**Compile:**
```bash
javac -encoding UTF-8 Solution.java
```

**Run with a large heap and larger stack, reading from a file:**
```bash
java -Xms1024m -Xmx4g -Xss64m -XX:+UseSerialGC Solution < input.txt > output.txt
```

**Why these choices:**
- `-Xms1024m -Xmx4g` — starting the heap pre-sized avoids repeated JVM heap resizing while you're reading a multi-hundred-MB input file (common in Hacker Cup's larger subtasks).
- `-Xss64m` — gives deep recursive solutions (DFS on large trees/graphs, recursive DP) more room before a `StackOverflowError`.
- `-XX:+UseSerialGC` — for a program that runs once and exits, the low-overhead Serial GC usually beats G1/Parallel GC, which are tuned for long-running services and spend extra time on background bookkeeping you don't need.

---

## ⚠️ The `-Xss` Main-Thread Gotcha

This trips up a lot of people, so it's worth calling out explicitly: **`-Xss` does not reliably control the stack size of the `main` thread on all platforms/JVM versions.** On many systems, the main thread's stack size is inherited from the OS (e.g. the `ulimit -s` value on Linux), not from `-Xss`.

**The reliable fix:** run your recursive solution inside a **new thread** with an explicit stack size:

```java
public class Solution {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(null, Solution::solve, "main-worker", 1 << 26); // 64MB stack
        worker.start();
        worker.join();
    }

    static void solve() {
        // your actual solution logic (can recurse deeply here)
    }
}
```

This guarantees the stack size regardless of platform, and is the standard workaround used in competitive programming for deep recursion (e.g. DFS on a skewed tree with 10⁶ nodes).

---

## 📦 Handling Large Inputs Efficiently

Big heap and stack settings won't help if your I/O is slow. Pair the JVM flags above with fast I/O:

- Use `BufferedReader` + `StreamTokenizer` (or a custom `FastReader`) instead of `Scanner` — `Scanner` is significantly slower for large inputs.
- Use `StringBuilder` and a single buffered write at the end instead of many small `System.out.println` calls.
- For very large outputs, wrap `System.out` in a `BufferedWriter`/`PrintWriter` and flush once at the end.

*(See `src/io/FastIO.java` in this repo for a ready-to-use template.)*

---

## 🧪 Recommended Presets by Scenario

### Small/medium constraints (typical Codeforces-style, local testing)
```bash
java -Xmx256m -Xss16m Solution < input.txt
```

### Large constraints (Meta Hacker Cup style, inputs in the 10⁷–10⁸ range)
```bash
java -Xms2g -Xmx6g -Xss64m -XX:+UseSerialGC Solution < input.txt > output.txt
```

### Deep recursion without touching `-Xss` (portable across platforms)
```bash
java -Xmx4g Solution < input.txt > output.txt
```
*(with the new-thread-and-explicit-stack-size pattern shown above handling the recursion depth instead)*

---

## 🖥️ IDE Configuration

**IntelliJ IDEA:**
`Run/Debug Configurations → VM options →`
```
-Xms1024m -Xmx4g -Xss64m -XX:+UseSerialGC
```

**VS Code (`launch.json`):**
```json
{
  "type": "java",
  "name": "Solution",
  "request": "launch",
  "mainClass": "Solution",
  "vmArgs": "-Xms1024m -Xmx4g -Xss64m -XX:+UseSerialGC"
}
```

---

## 🩺 Troubleshooting

| Symptom | Likely Cause | Fix |
|---|---|---|
| `StackOverflowError` on large recursion | Default stack too small, or `-Xss` ignored on main thread | Run logic in a new `Thread` with explicit stack size |
| `OutOfMemoryError: Java heap space` | `-Xmx` too low for input size | Increase `-Xmx`; check for unnecessary object allocation in loops |
| Program is correct but TLEs | Slow I/O (`Scanner`), or wrong GC choice | Switch to buffered I/O; try `-XX:+UseSerialGC` |
| Long JVM startup before your code even runs | JIT warm-up / large `-Xms` allocation | Lower `-Xms` slightly, or accept it — startup cost is usually negligible vs. total runtime budget in Hacker Cup |

---

## 📚 References

- [Oracle: java command-line options](https://docs.oracle.com/en/java/javase/17/docs/specs/man/java.html)
- [Meta Hacker Cup — Rules & FAQ](https://www.facebook.com/codingcompetitions/hacker-cup)

---

<div align="center">

These settings are starting points — always test locally against the actual input size for a problem before a contest, not during it.

</div>
