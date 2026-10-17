
## 2026-03-31 - Cache Pattern Compilation for Frequent Console & String Formatting
**Learning:** `ConsoleColor` methods `convertRGBColors` and `stripRGBColors` were compiling regex patterns (`Pattern.compile(...)`) on every console log line. In addition, checking for character presence (e.g., `input.indexOf('#') == -1`) before attempting regex matching provides a high-efficiency fast-path for the majority of non-RGB strings.
**Action:** Always pre-compile `Pattern` constants for high-frequency methods (such as logging/formatting utilities) and use fast-path character checks prior to regex execution.
