## 2025-05-18 - Avoid Repeated Regex Pattern Compilation in Hot Paths
**Learning:** `Pattern.compile(...)` in Java allocates and parses regex AST on every call. Executing `Pattern.compile` on every line formatted for console output or stripped of color codes introduces significant overhead and CPU load.
**Action:** Cache pre-compiled `Pattern` instances as `private static final` constants for common trigger characters (e.g. `'&'`) in console formatting hot paths.

## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.
