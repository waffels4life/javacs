# Javacs

A personal Java study workspace for learning the language, core APIs, concurrency, data structures, design principles, and small application experiments.

The repository is intentionally a **single Maven project**: exercises can share the same JDK and build configuration without introducing multi-module overhead too early.

## Project layout

- `src/main/java` — learning examples and small runnable exercises.
- `src/test/java` — automated tests for examples where behavior can be verified.
- `src/main/resources` — runtime resources.
- `src/test/resources` — test fixtures.
- `docs` — repository conventions and architecture notes.

Java packages should use lowercase names. Keep examples grouped by topic, and keep standalone experiments in a clearly named project package. Prefer descriptive class names over abbreviations when adding new material.

## Build

Requirements: JDK 22 and Maven.

```shell
mvn clean verify
```

Run a particular lesson by running its class from the IDE, or by using the Maven/Java run configuration appropriate for that class. The root `Main` class intentionally does not launch a specific lesson.

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

Use small, topic-focused commits:

- `concept:` introduce or explain a concept.
- `practice:` add an exercise or implementation.
- `note:` add learning notes or reference material.
- `refactor:` reorganize code without intentionally changing behavior.
- `test:` add or improve automated tests.
- `docs:` update documentation.
