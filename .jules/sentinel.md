## 2026-11-01 - Zip Slip Defense-in-Depth via FileUtil.ensureChild
**Vulnerability:** Zip extraction routines relying purely on string checks (`ensureSafeZipEntryName`) for path traversal can risk Zip Slip if unexpected path representations bypass the string checks or if exceptions thrown during extraction leak internals.
**Learning:** CloudNet has an established canonical path containment validation method, `FileUtil.ensureChild(root, child)`. Combining entry-name sanitization with path containment verification ensures defense-in-depth across diverse file systems and operating systems.
**Prevention:** Whenever writing or extracting entries resolved against a target directory, always invoke `FileUtil.ensureChild(targetDirectory, resolvedPath)` prior to file creation or stream transfer.
