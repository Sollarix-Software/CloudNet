## 2025-02-22 - ConsoleColor RGB Regex Compilation Hotpath

**Learning:** `ConsoleColor.toColoredString` and `stripColor` are called frequently when logging or printing output to the terminal. In the original code, `convertRGBColors` and `stripRGBColors` compiled a new regex `Pattern` on every call, and didn't check if the trigger character or RGB indicator `#` was even present before executing regex operations.

**Action:** Always pre-compile or cache regex patterns (e.g., in a `ConcurrentHashMap` if dynamically parameterized) and use fast `indexOf` short-circuit checks before performing expensive regex regex matching or string buffer allocations in hot output formatting paths.
