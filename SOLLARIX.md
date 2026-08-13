# Sollarix CloudNet fork

This fork is the CloudNet control-plane runtime used by SollarixMC.

## Baseline

- Upstream: `CloudNetService/CloudNet`
- Release: `4.0.0-RC16`
- Baseline commit: `c924e5ae8b619b6f0b71c6ed68b443280e8d17e2`
- Sollarix integration branch: `sollarix`

The baseline is intentionally pinned. Upstream `nightly` is treated as an input,
not as an automatically deployable release.

## Ownership boundary

CloudNet owns nodes, tasks, templates, service processes, maintenance state, and
actual service lifecycle. The Sollarix Go game core owns desired state,
idempotent operation identifiers, authorization, and audit history. Gameplay
logic must not be added to this fork.

Sollarix-specific CloudNet module and server bridge code lives in the private
`Sollarix-Software/SollarixMC` repository. Core changes belong here only when a
module cannot safely solve the problem.

## Verification

All builds and tests run in GitHub Actions. Developer workstations must not be
used for project verification.
