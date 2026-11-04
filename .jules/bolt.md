# Bolt's Performance Journal

## 2025-05-18 - Precompile regex Pattern in console color formatting hot paths
**Learning:** `ConsoleColor.convertRGBColors` and `ConsoleColor.stripRGBColors` are invoked on every line written to the node console. Recompiling `Pattern.compile(triggerChar + "#([\\da-fA-F]){6}")` repeatedly inside these methods created excessive Pattern object allocations and parsing overhead.
**Action:** Pre-compile static patterns for default trigger characters (such as `&`), and quote any dynamically formatted regex parts when non-default characters are passed.
