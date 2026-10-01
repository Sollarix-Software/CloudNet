## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.

## 2025-05-19 - Cache Compiled Regex Patterns in Console Log Formatting Hot Paths
**Learning:** `ConsoleColor` dynamically executed `Pattern.compile()` on every invocation of color formatting and stripping, creating unnecessary allocation churn and regex compilation overhead on high-throughput console output streams.
**Action:** Cache compiled regex `Pattern` instances by trigger character using `ConcurrentHashMap` and add fast-path `indexOf`/`contains` checks to bypass regex matching when color codes are absent.
