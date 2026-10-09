# Repository structure and conventions

## Principles

1. Keep one Maven module while this remains a learning workspace.
2. Organize examples by subject rather than when they were written.
3. Use lowercase Java package names matching directory names.
4. Keep each exercise easy to run in isolation; the root launcher must not run an arbitrary lesson.
5. Add tests when behavior is deterministic and worth verifying.
6. Keep Java under `src/main/java`, tests under `src/test/java`, and Markdown notes under `docs/`.

## Package taxonomy

- `fundamentals` — language syntax and operators.
- `oop` — classes, encapsulation, and interfaces.
- `api` — collections, streams, functional programming, I/O, files, and utility APIs.
- `concurrency` — threads and concurrency primitives.
- `algorithms` — data structures and algorithmic problems.
- `database` — SQL and JDBC.
- `security` — security API exercises.
- `design` — SOLID and design examples.
- `books` — book-related exercises.
- `projects` — standalone mini-projects in their own packages.

Only create packages when they contain relevant code; avoid empty scaffolding.

## Refactor checklist

- [x] Move source files and update their package declarations.
- [x] Update imports that reference moved classes.
- [x] Normalize moved package and directory names to lowercase.
- [x] Move Markdown learning notes out of the Java source tree.
- [x] Add a Maven verification workflow for pushes and pull requests.
- [ ] Continue adding focused tests as exercises mature.

Run `mvn clean verify` on JDK 22 before merging structural changes. Review the diff for unintended behavior changes.
