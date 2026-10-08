## 2026-03-31 - Cache regex Patterns in utility classes
**Learning:** `ConsoleColor` was compiling dynamic regex `Pattern` instances on every string color conversion and color stripping operation. Caching compiled `Pattern` instances using `ConcurrentHashMap` avoids repeated regex compilation overhead during frequent logging and terminal formatting.
**Action:** Always check utility classes handling formatting, logging, or string filtering for inline `Pattern.compile()` calls and cache compiled patterns.
