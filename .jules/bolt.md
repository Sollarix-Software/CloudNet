## 2026-03-30 - Console String Formatting Fast Path & Pattern Caching
**Learning:** `ConsoleColor.toColoredString` and `stripColor` are hot paths for console logs. Compiling `Pattern` for RGB colors on every string formatting call and instantiating `StringBuilder` even when no color codes exist adds regex and allocation overhead to string processing.
**Action:** Always check `input.indexOf(triggerChar) == -1` or `input.indexOf('#') == -1` to fast-path unformatted strings without allocation, and cache compiled `Pattern` instances per trigger character.
