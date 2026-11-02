# Bolt's Performance Journal

## 2026-04-16 - Avoid Dynamic Regex Compilation & Scans in Console Color Formatting
**Learning:** `ConsoleColor.toColoredString` and `stripColor` are hot console logging paths called on every console log line and prompt update. They previously compiled regex patterns dynamically on every call (`Pattern.compile(triggerChar + "#([\\da-fA-F]){6}")`), performed `LOOKUP.indexOf(...)` linear scans, and invoked expensive `Integer.decode()` + `String.format()`. Precompiling the default `&` pattern, caching arbitrary triggers, early-exiting when triggers are absent, using an array indexed lookup `BY_CHAR`, and parsing hex directly eliminates automaton compilation overhead and string allocations per log event.
**Action:** Always check whether string formatting utilities in logging or console display paths re-compile regular expressions or perform redundant linear scans.
