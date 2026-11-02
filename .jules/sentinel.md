# Sentinel's Journal

## 2026-11-02 - Path Traversal in LocalTemplateStorage File Operations
**Vulnerability:** In `LocalTemplateStorage`, template file operations (`createFile`, `newOutputStream`, `appendOutputStream`, `newInputStream`, `hasFile`, `deleteFile`, `fileInfo`, `createDirectory`, `listFiles`) resolved arbitrary user-supplied path strings directly against the template directory using `.resolve(path)` without validating containment, allowing path traversal (`../`) out of the template directory to read, write, or delete arbitrary files on the node filesystem.
**Learning:** Even when path traversal protections like `FileUtil.ensureChild` are applied in commands or service handlers, storage abstractions that expose direct file operations can be reached through internal RPC calls, module invocations, or plugins without prior validation.
**Prevention:** Always validate that child paths remain inside their intended root using `FileUtil.ensureChild(root, child)` directly at the storage boundary helper before performing filesystem operations.
