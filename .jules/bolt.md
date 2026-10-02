## 2025-05-18 - Avoid ConcurrentLinkedQueue.size() in Hot Paths
**Learning:** `ConcurrentLinkedQueue.size()` in Java is an $O(N)$ operation that traverses the linked nodes of the queue. Calling `.size()` on every log line in a high-throughput console log stream causes severe performance degradation as cache size grows.
**Action:** Use an explicit `AtomicInteger` counter to track size in $O(1)$ time whenever `ConcurrentLinkedQueue` size checks are needed in hot paths.

## 2026-10-02 - Cache Compiled Patterns and Use Fast-Path String Inspection in Console Formatting
**Learning:** Re-compiling `Pattern` instances or running regex replacements on every console line in hot logging paths causes excessive object allocation and CPU overhead. Checking `String.indexOf` first allows skipping regex evaluation and `StringBuilder` creation entirely for unformatted strings.
**Action:** Always cache compiled `Pattern` instances (e.g. in `ConcurrentHashMap`) and check `indexOf` for required trigger characters before invoking regex matching in high-frequency string processing methods.
