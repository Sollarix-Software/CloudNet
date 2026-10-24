## 2025-05-18 - Avoid unnecessary String allocation in WildcardUtil.fixPattern
**Learning:** `String.replace("*", ".*")` allocates a new string even when no `*` character is present in the target string. Checking `regex.indexOf('*') != -1` first avoids heap allocation in non-wildcard queries.
**Action:** When performing regex or string replacements on strings that frequently do not contain target search characters (e.g., direct names/identifiers without wildcards), check `indexOf` first before performing string replacements.
