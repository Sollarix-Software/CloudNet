# Sentinel Security Journal

## 2025-05-18 - Path Traversal Prevention in Zip Extraction
**Vulnerability:** Zip extraction (`ZipUtil.extractEntry`) relied on `ensureSafeZipEntryName` which only checked string patterns (e.g. `contains("..")`), potentially missing complex path normalization edge cases or traversal paths.
**Learning:** Checking entry names via string checks is insufficient on its own. Using path containment validation via `FileUtil.ensureChild(targetDirectory, file)` guarantees canonical path containment before performing file system operations.
**Prevention:** Always validate that resolved target paths are strictly children of the expected destination path using `FileUtil.ensureChild(root, child)` before creating files or directories.
