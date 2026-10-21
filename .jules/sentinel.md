## 2025-05-18 - Defense-in-depth Path Traversal Check in Zip Extraction
**Vulnerability:** Potential Zip Slip path traversal during ZIP extraction if `ensureSafeZipEntryName` checks are bypassed or extended with complex paths.
**Learning:** Even though `ZipUtil` had an `ensureSafeZipEntryName` check, adding `FileUtil.ensureChild(targetDirectory, file)` directly before file creation provides a solid defense-in-depth layer ensuring canonical path containment.
**Prevention:** Always enforce path containment via `FileUtil.ensureChild(root, child)` whenever constructing output file paths from external input names.
