# Sentinel's Security Journal

## 2026-11-11 - Zip Slip Path Traversal Defense in ZipUtil
**Vulnerability:** In `ZipUtil`, zip entries were only checked with rudimentary string checks (`ensureSafeZipEntryName`), and exceptions during entry extraction were limited to `IOException`, potentially allowing unhandled runtime exceptions or edge cases to bypass directory containment.
**Learning:** Naive string checks (e.g. `contains("..")`) should be paired with canonical path resolution using `FileUtil.ensureChild(targetDirectory, file)` to guarantee defense-in-depth against Zip Slip path traversal. Catching `Exception` in stream extraction ensures runtime errors from validation checks fail safely without unhandled crashes.
**Prevention:** Always enforce `FileUtil.ensureChild(targetDirectory, resolvedPath)` when extracting archives or resolving untrusted paths to the filesystem.
