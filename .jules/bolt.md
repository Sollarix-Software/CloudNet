## 2026-10-31 - Indexed traversal over collections in formatters
**Learning:** Utilities accepting generic `Collection` types and iterating them by index (e.g. `list.get(i)`) should ensure the underlying collection implements `RandomAccess`. Converting non-`RandomAccess` collections to `ArrayList` prevents hidden O(N^2) traversal costs and per-element node allocation overheads.
**Action:** When transforming collections for indexed lookups, check `input instanceof RandomAccess && input instanceof List` before falling back to `new ArrayList<>(input)`.
