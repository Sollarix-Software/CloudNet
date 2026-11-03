# Sentinel's Journal

Critical security learnings and patterns discovered in the codebase.

## 2025-05-18 - Path traversal mitigation in zip extraction
**Vulnerability:** Zip Slip / Path Traversal in archive extraction (`UnzipStepExecutor`). Using naive checks like `!targetPath.normalize().startsWith(workingDirectory)` on relative/unnormalized paths can fail or be inconsistent across different path resolutions and parent directory structures.
**Learning:** CloudNet provides a standardized, rigorous containment verification method `FileUtil.ensureChild(root, child)` that normalizes and converts both the root and child paths to absolute paths before asserting containment (`startsWith` and `getNameCount`). Additionally, zip extraction must create parent directories before writing nested files to avoid `NoSuchFileException` during extraction of non-explicitly listed parent directories.
**Prevention:** Always use `FileUtil.ensureChild(baseDir, targetPath)` for path containment and traversal checks across any file and archive extraction logic. Ensure parent directories are created with `FileUtil.createDirectory(targetPath.getParent())` prior to opening streams or writing files.
