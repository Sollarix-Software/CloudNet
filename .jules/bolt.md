## 2026-03-24 - Avoid Dynamic Pattern.compile in Hot Console Logging Paths
**Learning:** In CloudNet's console processing pipeline, `ConsoleColor` methods (`toColoredString` and `stripColor`) are called frequently for every log line and prompt update. Dynamic `Pattern.compile` calls inside string formatting methods cause heavy regex AST construction and heap allocations on every line.
**Action:** Always precompile static regex patterns or cache them in a `ConcurrentHashMap` for dynamic keys when handling frequent string formatting operations.
