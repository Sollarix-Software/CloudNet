# Sentinel Journal

## 2026-10-31 - Defense-in-depth Zip Slip Prevention with FileUtil.ensureChild
**Vulnerability:** Zip extraction utilities relying solely on entry name string checks (`ensureSafeZipEntryName`) can be vulnerable if path resolution or normalization edge cases occur across different platforms or filesystems.
**Learning:** Checking string patterns (`name.contains("..")`) in entry names is a good fast path, but resolving the target path and enforcing root path containment via `FileUtil.ensureChild(targetDirectory, file)` guarantees that no file or directory is created outside the intended destination directory.
**Prevention:** Always pair input name sanitization with canonical path containment checks via `FileUtil.ensureChild(root, child)` before creating directories or writing file contents.
