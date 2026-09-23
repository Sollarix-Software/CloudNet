## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.
