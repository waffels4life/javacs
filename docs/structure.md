# Repository structure and conventions

## Guiding principles

1. Keep one Maven module while this repository is primarily a learning workspace.
2. Organize examples by subject, not by the date they were written.
3. Use lowercase Java package names that match their directory names.
4. Keep each example understandable in isolation; avoid a global launcher that silently runs a lesson.
5. Add automated tests when an example has deterministic behavior worth verifying.
6. Put prose notes in Markdown files under `docs/` or next to the topic they explain, not under Java source directories.

## Current migration scope

This refactor establishes repository-level conventions and normalizes the download-manager package path. Existing topics are not all moved at once: each additional package migration should update its source path, package declaration, imports, and references in the same commit.

## Suggested destination taxonomy

- `fundamentals/` — syntax, primitive types, control flow, methods, exceptions.
- `oop/` — classes, encapsulation, inheritance, interfaces, polymorphism.
- `api/` — collections, streams, I/O, date/time, reflection, annotations.
- `concurrency/` — threads, synchronization, executors, futures.
- `algorithms/` — data structures, complexity, searching, sorting, problem solving.
- `design/` — SOLID and design-pattern examples.
- `database/` — SQL and JDBC.
- `security/` — cryptography, hashing, secure coding.
- `projects/` — independent mini-projects, each in its own lowercase package.

These are logical destinations, not a requirement to create empty packages in advance.

## Refactor checklist

- [ ] Move source files and update their `package` declarations together.
- [ ] Update every affected import and fully qualified reference.
- [ ] Rename package directories to lowercase.
- [ ] Keep Java source under `src/main/java` and tests under `src/test/java`.
- [ ] Run `mvn clean verify` on JDK 22.
- [ ] Review the diff for accidental behavior changes before merging.
