## 2025-05-18 - Avoid dynamic Pattern compilation on console log formatting path
**Learning:** `ConsoleColor` was compiling a regex pattern dynamically on every single log string conversion call (`convertRGBColors` and `stripRGBColors`), causing repeated string parsing and Pattern compilation overhead on hot logging paths.
**Action:** Always pre-compile `Pattern` instances as static constants or cache them when processing hot path strings (such as console log outputs).
