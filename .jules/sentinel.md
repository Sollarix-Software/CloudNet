## 2026-03-31 - Path Traversal Prevention in Zip Extraction
**Vulnerability:** Zip extraction in `ZipUtil.extractEntry` used string/path character checks which could be bypassed or miss edge-cases if not verified against the target root path.
**Learning:** Checking entry names string patterns alone may miss path resolution semantics. `FileUtil.ensureChild(targetDirectory, file)` validates normalized absolute path containment.
**Prevention:** Always enforce path containment via `FileUtil.ensureChild(root, child)` when resolving user-controlled relative paths against a target base directory.
