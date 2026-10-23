## 2026-03-30 - Enforce FileUtil.ensureChild for archive extraction
**Vulnerability:** Incomplete path traversal validation during zip extraction in `ZipUtil` and `UnzipStepExecutor`.
**Learning:** Custom path prefix checks like `targetPath.normalize().startsWith(workingDirectory)` or string checks like `ensureSafeZipEntryName` can miss edge cases compared to standardized `FileUtil.ensureChild(root, child)` path containment validation.
**Prevention:** Always use `FileUtil.ensureChild(root, child)` whenever resolving child paths from untrusted input before reading or writing files.
