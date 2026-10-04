## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.

## 2025-05-19 - Pre-compile Regex Patterns in Log Formatting Hot Paths
**Learning:** `Pattern.compile()` compiles regex expressions into DFA/NFA state machines at runtime. Compiling patterns inside console string color formatting methods executed on every log entry causes unnecessary CPU overhead and allocation churn.
**Action:** Pre-compile static `Pattern` constants for default trigger characters (e.g. `&`) in text processing and logging helpers.
