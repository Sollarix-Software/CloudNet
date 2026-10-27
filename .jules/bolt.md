# Bolt's Journal - Critical Learnings

This journal tracks critical performance learnings, bottlenecks, and anti-patterns in CloudNet.


## 2026-03-29 - Cache RGB Pattern compilation and add early exits in ConsoleColor
**Learning:** Re-compiling dynamic regex Patterns (`Pattern.compile(...)`) on every console log line or prompt string conversion creates unnecessary memory allocations and CPU overhead in the hot logging path.
**Action:** Cache compiled `Pattern` objects in a `ConcurrentHashMap` keyed by trigger character and add fast-path `indexOf(triggerChar)` pre-checks before executing string replacements or allocations.
