# Bolt's Journal

## 2025-10-26 - Pre-compiled Regex Patterns and Fast-Path Checking for Console Color Formatting
**Learning:** `ConsoleColor.toColoredString` and `stripColor` are called frequently on console output. Recompiling `Pattern.compile` on every string conversion and creating StringBuilders when no trigger character exists generates unnecessary allocations and CPU overhead.
**Action:** Always check `indexOf(triggerChar) == -1` to early return when formatting is not required, and pre-compile static `Pattern` instances for common trigger characters like `'&'`.
