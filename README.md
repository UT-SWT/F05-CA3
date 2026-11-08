# F05-CA3
# Elevator Review Project

A deliberately small elevator simulator for the Software Testing course review exercise.

## Requirements
- JDK 17+
- Maven 3.8+

## Run
```bash
mvn test
```

## Project structure
- `src/main/java`: elevator model, services, adapters, event notification and application wiring.
- `src/test/java`: pre-written JUnit 5 tests to review.
- `src/test/resources`: a small fixture file used by one test.

The project is intentionally a review target: it contains a mixture of testability-oriented design choices and test/code quality problems. Students should identify and explain findings using **only** the concepts covered in lecture slides 12 (Design for Testability) and 13 (Code Smells). No fixes are expected as part of the review.
