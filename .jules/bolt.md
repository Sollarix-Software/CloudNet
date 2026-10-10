# Bolt's Journal - Critical Performance Learnings

## 2025-05-18 - Avoid repeated Regex compilation and string allocations in console color formatting
**Learning:** `ConsoleColor.toColoredString` and `ConsoleColor.stripColor` are called frequently during console logging and prompt rendering. Calling `Pattern.compile()` on every invocation and running regex matching without checking if trigger characters exist creates unnecessary object allocations.
**Action:** Always pre-compile/cache regex patterns and add fast-path checks using `String.indexOf()` before invoking regex operations or string builders.
