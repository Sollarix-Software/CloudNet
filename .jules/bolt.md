## 2025-05-18 - Cache compiled Regex Patterns in hot log formatting paths
**Learning:** Re-compiling `Pattern` via `Pattern.compile()` on every log statement or console string formatting call creates unnecessary object allocations and CPU overhead in high-throughput logging paths.
**Action:** Always cache compiled `Pattern` instances (or use a thread-safe cache such as `ConcurrentHashMap` for dynamic pattern keys) in utility methods invoked during continuous log processing or string stripping.
