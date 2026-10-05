# Bolt's Journal - Critical Learnings

## 2025-05-18 - ConsoleColor RGB Pattern Compilation & Formatting Optimization
**Learning:** `ConsoleColor` was compiling `Pattern.compile(triggerChar + "#([\\da-fA-F]){6}")` and calling `String.format(RGB_ANSI, ...)` on every string formatted or stripped for the console. In CLI logging components, frequent regex compiling and `String.format` call site overhead add allocation and execution cost to every console message.
**Action:** Pre-compile static patterns for default trigger characters (`&`) and replace `String.format` with fast integer-to-string concatenation (`"\u001B[38;2;" + r + ";" + g + ";" + b + "m"`).
