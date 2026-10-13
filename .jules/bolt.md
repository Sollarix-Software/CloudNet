## 2025-05-18 - Avoid Repeated Pattern Compilation in Console Formatting Hot-Path
**Learning:** `ConsoleColor.java` ran `Pattern.compile(...)` on every single console output line to convert or strip RGB color strings, leading to excessive `Pattern` and regex matcher object allocations per logged line.
**Action:** Pre-compile static `Pattern` instances for common trigger characters (like `'&'`) and add `indexOf` short-circuit checks to bypass regex matching when input strings do not contain color triggers.
