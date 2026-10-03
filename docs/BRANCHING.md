# Conversa Git Branching Strategy

## Branches

- `main`: production-ready code only.
- `develop`: integration branch for completed features.
- `feature/<feature-name>`: new product functionality; branches from `develop`.
- `fix/<issue-name>`: non-production bug fixes; branches from `develop`.
- `refactor/<area>`: structural/code-quality changes.
- `test/<area>`: test-focused changes.
- `docs/<topic>`: documentation-only changes.
- `chore/<task>`: tooling, dependency, or repository maintenance.
- `hotfix/<issue-name>`: urgent production fixes; branches from `main`.

## Rules

1. Never commit feature work directly to `main` or `develop`.
2. Every logical feature gets its own branch.
3. Feature branches start from the latest `develop`.
4. Pull requests are required to merge feature work into `develop`.
5. Release-ready changes move from `develop` to `main`.
6. Keep branches short-lived and focused.
7. Use lowercase kebab-case branch names.
8. Do not use phase-numbered branch names such as `phase-00` or `phase-01`.
9. Use Conventional Commits, for example:
   - `feat(auth): add user registration`
   - `fix(messaging): handle duplicate delivery`
   - `chore(repo): update development tooling`
10. Delete merged feature branches after merge unless there is a concrete reason to retain them.

## Standard Flow

`main` <- `develop` <- `feature/<feature-name>`

For an urgent production fix:

`main` <- `hotfix/<issue-name>`

After a hotfix, the fix must also be brought back into `develop`.
