## 2025-05-18 - Cache compiled RGB patterns in ConsoleColor

**Learning:** Repeatedly compiling regex `Pattern` instances on every line of console logging/formatting (e.g. `Pattern.compile(triggerChar + "#([\\da-fA-F]){6}")`) causes unnecessary object allocation and parsing overhead in high-throughput console applications. Caching compiled `Pattern` objects per trigger character in a `ConcurrentHashMap` and adding early returns (`indexOf(triggerChar) == -1`) eliminates regex compilation overhead and unnecessary allocations for plain text messages.
**Action:** Always check for hot string/regex parsing paths in logging or formatting utilities and cache compiled `Pattern` instances or add fast-path early returns when special characters are absent.
