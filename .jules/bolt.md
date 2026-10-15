# Bolt's Journal

## 2026-03-31 - Cache compiled RGB regex patterns in ConsoleColor
**Learning:** `ConsoleColor.convertRGBColors` and `ConsoleColor.stripRGBColors` compiled a new `Pattern` on every line formatted/stripped for output. Caching compiled `Pattern` instances per trigger character using `ConcurrentHashMap` avoids redundant regex compilation during high-frequency console output.
**Action:** Always check for repeated `Pattern.compile` calls inside hot utility methods like formatting or logging routines and cache thread-safe `Pattern` instances.
