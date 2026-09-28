## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.

## 2025-09-28 - Fast-path & Pattern Reuse for High-Volume Console Color Parsing
**Learning:** Dynamic regex compilation via `Pattern.compile` and runtime format specifier parsing with `String.format` / `Integer.decode` in high-throughput console line formatting causes significant CPU and object allocation overhead.
**Action:** Always check `indexOf(triggerChar)` as a fast-path, compile static/cached `Pattern`s, parse hex ints directly with `Integer.parseInt`, and use direct string concatenation for color sequences.
