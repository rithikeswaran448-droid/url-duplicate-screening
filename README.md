URL Duplicate Screening System

A memory-efficient URL deduplication and screening pipeline in Java designed to evaluate the trade-offs between exact membership testing (HashSet) and probabilistic membership queries (BloomFilter).

📌 Project Overview

Web crawlers and search indexers process billions of URLs daily. Storing every unique URL in an exact in-memory set quickly exhausts available RAM. This project benchmarks and demonstrates a dual-screening architecture:

1.Probabilistic Layer (Bloom Filter): Extremely compact bit-array representation with zero false negatives and a bounded false positive rate ($p$).

2.Exact Ground-Truth Layer (ExactSetEvaluator / HashSet): Unambiguous exact set used to detect duplicates, measure actual observed false positives, and estimate heap memory consumption.

3.URL Normalizer (UrlNormalizer): Canonicalizes URLs prior to hashing and insertion to prevent false variations caused by protocol variations, query ordering, or trailing slashes.
