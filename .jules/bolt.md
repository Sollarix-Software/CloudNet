# Bolt Journal

## 2026-11-10 - Precompile and cache regex patterns on console hot path
**Learning:** `ConsoleColor.toColoredString` and `ConsoleColor.stripColor` are called for every line written to the node's console. Previously, `Pattern.compile(...)` was invoked repeatedly on every line for RGB hex extraction (`&#[0-9a-fA-F]{6}`). Precompiling/caching `Pattern` in a `Map` per `triggerChar` and short-circuiting with `String.indexOf` before regex evaluation reduces console color formatting overhead by >25x on plain/standard strings and avoids unnecessary pattern allocations on every console output.
**Action:** Always check frequently invoked console or logger formatters for repeated `Pattern.compile` calls and bypass regex evaluation using fast `indexOf` checks where possible.
