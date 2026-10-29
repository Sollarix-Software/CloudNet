# Bolt's Performance Journal

## 2025-05-18 - Avoid Repeated Regex Compilation in Console Color Processing
**Learning:** `ConsoleColor.convertRGBColors` and `stripRGBColors` dynamically compiled regular expressions (`Pattern.compile(...)`) on every color formatting or stripping call. When processing high-throughput console output / log lines, this repeatedly allocated and parsed regex ASTs.
**Action:** Cache compiled `Pattern` instances by trigger character (e.g., using `ConcurrentHashMap.computeIfAbsent`) to ensure regex pattern compilation occurs at most once per trigger character.
