## 2025-02-28 - Avoid ConcurrentLinkedQueue.size() in Hot Paths

**Learning:** `ConcurrentLinkedQueue.size()` traverses the entire queue node by node in $O(N)$ time. When called on every log line in `AbstractServiceLogCache` to enforce capacity limits, it creates significant CPU overhead during high log volume (e.g., service startup).
**Action:** Use an `AtomicInteger` size counter alongside `ConcurrentLinkedQueue` to track and enforce size limits in $O(1)$ time in hot execution paths.
