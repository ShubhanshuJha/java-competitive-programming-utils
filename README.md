# Java Competitive Programming Utils

**A curated, battle-tested collection of reusable Java utilities, data structures, and algorithms for competitive programming.**

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)
[![Stars](https://img.shields.io/github/stars/ShubhanshuJha/java-competitive-programming-utils?style=social)](https://github.com/ShubhanshuJha/java-competitive-programming-utils/stargazers)

---

## 📖 Overview

Competitive programming often means rewriting the same segment tree, DSU, or fast I/O template under time pressure during a contest. This repository is a **personal toolkit turned open resource** — a growing library of clean, well-tested Java implementations for the data structures and algorithms that show up again and again on **Codeforces, LeetCode, AtCoder, HackerRank, and ICPC**.

The goal: **copy, paste, solve, repeat** — without re-deriving the same logic every time.

---

## ✨ Features

### 🔢 Fast I/O
- Custom `FastReader` / `FastWriter` for competitive-programming-speed input/output
- BufferedReader/StreamTokenizer based templates

### 🌲 Data Structures
- Segment Tree (with lazy propagation)
- Fenwick Tree / Binary Indexed Tree
- Disjoint Set Union (Union-Find with path compression & union by rank)
- Trie
- Sparse Table
- Min/Max Heap utilities

### 🔗 Graph Algorithms
- BFS / DFS templates
- Dijkstra, Bellman-Ford, Floyd-Warshall
- Kruskal's & Prim's MST
- Topological Sort
- Lowest Common Ancestor (LCA) with binary lifting
- Tarjan's SCC

### 🔡 String Algorithms
- KMP Pattern Matching
- Z-Algorithm
- Trie-based string search
- Suffix Array (basic)

### 🧮 Number Theory & Math
- Sieve of Eratosthenes
- Modular exponentiation & modular inverse
- GCD / LCM utilities
- Prime factorization

### 📊 Sorting & Searching
- Custom comparators and sorting utilities
- Binary Search variants (lower bound, upper bound)

---

## 📂 Repository Structure

```
java-competitive-programming-utils/
├── src/
│   ├── io/                 # Fast I/O utilities
│   ├── data-structures/     # Segment Tree, DSU, Trie, Fenwick Tree, etc.
│   ├── graphs/             # Graph traversal & shortest-path algorithms
│   ├── strings/            # String matching algorithms
│   ├── arrays/              # Array based algorithms
|   ├── maths/              # Number theory utilities
|   ├── dynamic-programming/              # Number theory utilities
│   └── sorting/            # Sorting & searching utilities
├── examples/                # Sample usage & problems solved using these utils
├── README.md
├── LICENSE
└── CONTRIBUTING.md
```

---

## 🚀 Usage

Clone the repository:

```bash
git clone https://github.com/ShubhanshuJha/java-competitive-programming-utils.git
cd java-competitive-programming-utils
```

Import any utility class directly into your solution file, e.g.:

```java
import datastructures.DisjointSetUnion;

public class Solution {
    public static void main(String[] args) {
        DisjointSetUnion dsu = new DisjointSetUnion(10);
        dsu.union(1, 2);
        System.out.println(dsu.find(1) == dsu.find(2)); // true
    }
}
```

> 💡 Tip: Most competitive judges only accept a single file. Copy the relevant class body directly into your submission file when needed.

---

## 🤝 Contributing

Contributions are welcome! If you have a clean, tested implementation of a common CP algorithm or data structure:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/segment-tree-persistent`)
3. Add your utility with clear comments and (ideally) a usage example
4. Submit a pull request

Please keep implementations **generic, well-commented, and free of unnecessary dependencies** so they stay copy-paste friendly for contests.

---

## 👤 Author

**Shubhanshu Jha**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/shubhanshu-jha)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)](https://github.com/ShubhanshuJha)

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

If this repo helped you, consider giving it a ⭐ — it helps others discover it too.

</div>
