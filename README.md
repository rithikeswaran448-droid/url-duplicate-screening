URL Duplicate Screening System

A memory-efficient URL deduplication and screening pipeline in Java designed to evaluate the trade-offs between exact membership testing (HashSet) and probabilistic membership queries (BloomFilter).

📌 Project Overview

Web crawlers and search indexers process billions of URLs daily. Storing every unique URL in an exact in-memory set quickly exhausts available RAM. This project benchmarks and demonstrates a dual-screening architecture:

1.Probabilistic Layer (Bloom Filter): Extremely compact bit-array representation with zero false negatives and a bounded false positive rate ($p$).

2.Exact Ground-Truth Layer (ExactSetEvaluator / HashSet): Unambiguous exact set used to detect duplicates, measure actual observed false positives, and estimate heap memory consumption.

3.URL Normalizer (UrlNormalizer): Canonicalizes URLs prior to hashing and insertion to prevent false variations caused by protocol variations, query ordering, or trailing slashes.

🧱 Architecture & Modules
src/
├── Main.java               # Execution harness, benchmark runner, and report generator
├── BloomFilter.java        # Probabilistic bit array using optimal hash functions
├── ExactSetEvaluator.java  # Exact ground-truth HashSet and benchmark comparison engine
└── UrlNormalizer.java      # URL canonicalization and sanitization logic

1. ExactSetEvaluator.java (Evaluation & Benchmarking)
Exact Deduplication: Maintains a HashSet<String> as the authoritative truth.
Accuracy Measurement: Evaluates queries over unseen URL sets via countFalsePositives() and observedFpRate().
Heap Memory Estimation: Computes the runtime footprint of string retention in Java heap.

2. BloomFilter.java (Probabilistic Screener)
Employs $k$ independent hash functions over an $m$-bit array.
Provides $O(k)$ insertion and lookup time complexity independent of URL string length.
Guarantees:
If mightContain(url) == false: The URL has definitely not been seen.
If mightContain(url) == true: The URL has likely been seen, with a small probability of a false positive.

3. UrlNormalizer.java (Canonicalization Engine)
Standardizes protocol schemes (http vs https).
Strips redundant trailing slashes, fragments (#), and normalizes query parameter order so identical endpoints map to identical hash digests.

4. Main.java (Driver & Benchmarks)
Feeds synthetic and realistic URL datasets through the pipeline.
Outputs direct comparisons of:
Total items processed vs duplicates filtered
Observed false positive rate vs theoretical Bloom filter expectation
Memory savings achieved (Bloom Filter bits vs Exact Set bytes)
