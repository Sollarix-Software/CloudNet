## 2026-09-25 - Cross-Platform Zip Slip and Path Traversal Validation
**Vulnerability:** OS-dependent character validation (`(name.contains(":") && IS_WINDOWS)`) in zip entry name checks permitted entries with colons on non-Windows platforms, and extraction did not enforce canonical path containment.
**Learning:** OS-dependent validation checks in zip processing can lead to cross-platform path traversal vulnerabilities when archives are handled in multi-OS or containerized environments.
**Prevention:** Always perform canonical path containment checks (`FileUtil.ensureChild(targetDirectory, file)`) and enforce OS-agnostic entry name sanitization.
