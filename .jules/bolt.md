## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.

## 2025-05-19 - Cache Dynamic Pattern Compilation & Fast-Path Non-Matching Inputs
**Learning:** Re-compiling regular expressions dynamically using `Pattern.compile()` on every invocation of frequent string processing helper methods (such as `ConsoleColor.toColoredString`) causes heavy CPU overhead and allocations. Additionally, running regex matching without checking for trigger characters forces unnecessary `StringBuilder` allocations.
**Action:** Cache compiled `Pattern` objects in thread-safe maps (`ConcurrentHashMap`) keyed by trigger parameters, and use fast-path checks like `input.indexOf(triggerChar) == -1` to skip allocations entirely when processing plain input.
