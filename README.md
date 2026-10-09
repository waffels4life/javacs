# Javacs

A personal Java study workspace for learning the language, core APIs, concurrency, data structures, design principles, and small application experiments.

The repository is intentionally a **single Maven project**: exercises share one JDK/build configuration without premature multi-module overhead.

## Project layout

- `src/main/java/com/javacs/fundamentals` — language fundamentals and operators.
- `src/main/java/com/javacs/oop` — object-oriented programming and interfaces.
- `src/main/java/com/javacs/api` — collections, functional programming, streams, I/O, files, and utility APIs.
- `src/main/java/com/javacs/concurrency` — threads and concurrency primitives.
- `src/main/java/com/javacs/algorithms` — algorithmic exercises and problem solving.
- `src/main/java/com/javacs/database` — SQL and JDBC learning examples.
- `src/main/java/com/javacs/security` — Java security API exercises.
- `src/main/java/com/javacs/design` — SOLID principles and design examples.
- `src/main/java/com/javacs/projects` — independent mini-projects.
- `src/main/java/com/javacs/books` — examples and notes inspired by books.
- `src/test/java` — automated tests.
- `docs` — repository conventions and learning notes.

Java package names use lowercase. Java sources stay under `src/main/java`; Markdown notes belong under `docs/`. Keep examples grouped by subject, and update package declarations and imports together when moving a class.

## Build

Requirements: JDK 22 and Maven.

```shell
mvn clean verify
```

Run a specific lesson from its class in your IDE. The root `Main` class is deliberately neutral and does not launch one particular exercise.

## Learning resources

- [Dev.java](https://dev.java/learn/)
- [Java roadmap](https://roadmap.sh/java)
- [Jenkov Java tutorials](https://jenkov.com/tutorials/java/index.html)
- [HowToDoInJava](https://howtodoinjava.com/)
- [TheAlgorithms/Java](https://github.com/TheAlgorithms/Java)

### Books

- *Core Java, Volume I & II* — Cay S. Horstmann
- *Data Structures and Algorithms in Java* — Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
- *Effective Java* — Joshua Bloch

## Commit conventions

Prefer small, topic-focused commits:

- `concept:` explain or introduce a concept.
- `practice:` add an exercise or implementation.
- `note:` add learning notes.
- `refactor:` reorganize code without intentionally changing behavior.
- `test:` add or improve automated tests.
- `docs:` update documentation.
