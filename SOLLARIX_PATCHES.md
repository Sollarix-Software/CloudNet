# Sollarix patch registry

Every core patch must be listed before it is merged into `sollarix`.

| Patch | Upstream issue | Reason | Modules | Upstreamable | Status |
|---|---|---|---|---|---|
| Restore md-5 snapshots repository | Not filed; [reproducer](https://github.com/Sollarix-Software/CloudNet/actions/runs/31517496124) | RC16 requests `net.md-5:bungeecord-chat:1.21-R0.5-SNAPSHOT`, which is absent from its configured repositories | `build-extensions`, `ext:adventure-helper` | Yes | CI validation |

## Patch requirements

Each patch is a small, independently revertible commit and records:

1. a reproducer or upstream issue;
2. the observed and expected behavior;
3. affected modules and compatibility risk;
4. a GitHub Actions test preventing regression;
5. whether the change can be submitted upstream.

## Upstream synchronization

1. Fetch `CloudNetService/CloudNet` as remote `upstream`.
2. Review changes from `upstream/nightly`; never merge them automatically.
3. Create a temporary synchronization branch from `sollarix`.
4. Merge or cherry-pick selected upstream commits.
5. Resolve conflicts without removing Sollarix regression tests.
6. Merge through a pull request only after the build-only workflow succeeds.
