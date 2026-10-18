## 2026-03-31 - Cache compiled RGB Pattern instances in ConsoleColor
**Learning:** `ConsoleColor` compiled `Pattern.compile(triggerChar + "#([\\da-fA-F]){6}")` on every log line in `toColoredString` and `stripColor`. Pre-compiling `Pattern` for the standard `'&'` trigger char avoids regex recompilation overhead on hot console logging paths.
**Action:** When regular expressions are evaluated frequently (like log formatting or event processing), cache `Pattern` constants for common default inputs rather than calling `Pattern.compile` on demand.
