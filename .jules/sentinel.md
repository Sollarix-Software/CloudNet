## 2025-05-18 - Cross-Platform Zip Extraction Path Verification
**Vulnerability:** Zip extraction (`ZipUtil`) relied solely on checking entry string names with platform-dependent restrictions (`IS_WINDOWS`), leaving potential gaps for path traversal / Zip Slip or cross-platform archive unpacking.
**Learning:** `FileUtil.ensureChild(root, child)` was already available in the codebase for canonical path containment verification, but was not used during Zip extraction. Also, ZIP archive entries must be sanitized consistently regardless of the host OS since archives can be created on or moved between different platforms.
**Prevention:** Always combine entry string sanitization with canonical path validation (`FileUtil.ensureChild`) when handling zip files or user-supplied file paths.
