## 2025-05-18 - Avoid Pattern.compile in console log formatting hot paths

**Learning:** `ConsoleColor.toColoredString` and `stripColor` are called on every single line printed to console or written to logs. Re-compiling RGB `Pattern` instances on every method invocation causes significant unnecessary GC pressure and CPU overhead in string processing. Furthermore, most plain log messages do not contain trigger chars like `&`, making a fast-path `input.indexOf(triggerChar) == -1` check extremely effective at bypassing all `StringBuilder` and regex matcher allocations.
**Action:** Always check for character presence before starting string/regex transformations, and cache compiled `Pattern` instances in static thread-safe data structures (`ConcurrentHashMap`).
