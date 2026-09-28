## 2025-05-18 - Standard Path Normalization for Zip Slip Defense
**Vulnerability:** `ZipUtil.extractEntry` used custom string checks (`name.contains("..")`) for zip entry names without path normalization and prefix validation.
**Learning:** String-based path validation on Zip entry names can miss OS-specific path representation edge cases. In Java, path normalization via `toAbsolutePath().normalize()` and checking `file.startsWith(normalizedTarget)` is the standard mechanism to prevent Zip Slip path traversal.
**Prevention:** Always normalize target and resolved paths and verify `resolvedPath.startsWith(normalizedTargetDir)` when extracting archives.
