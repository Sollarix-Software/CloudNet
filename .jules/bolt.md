# Bolt's Performance Journal

## 2026-03-31 - Cache compiled regex pattern and fast-path string checks in ConsoleColor
**Learning:** `ConsoleColor.toColoredString` and `ConsoleColor.stripColor` are called frequently across console logging and command rendering paths. Compiling regex patterns repeatedly inside formatting methods causes avoidable GC pressure and CPU overhead. Additionally, checking for trigger characters with `indexOf` enables early returns that completely skip regex matching and StringBuilder allocations for unformatted strings.
**Action:** When working on string coloring or formatting utilities, always cache compiled `Pattern` instances and provide fast-path early exits when trigger characters are absent.
