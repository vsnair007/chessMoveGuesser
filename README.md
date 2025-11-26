# Chess Move Guesser

A small Spring Boot service that computes possible chess moves for a given piece and board position. It uses a strategy pattern for piece-specific move logic and caches results to improve performance.

- Project: `chessMoveGuesser`
- Language: Java (Spring Boot, Maven)
- Purpose: Provide an algorithmic engine (and REST endpoint) to return valid target positions for chess pieces on a configurable board.

## Key features

- Piece-specific move strategies (knight, king, queen, pawn, etc.)
- Board validation with configurable board size
- Caffeine-backed caching for repeated queries
- Clean service layer (`MoveGuesserServiceImpl`) and pluggable `MoveStratergyFactory`
- Unit tests under `test/` (JUnit 5 + Mockito)

## Quick start

Prerequisites
- Java 21 (or the JDK targeted by the project POM)
- Git
- Maven (optional — the repository includes the Maven wrapper)
- Windows note: use the included wrapper script `mvnw.cmd` when on Windows
- Docker

Clone
```bash
git clone https://github.com/vsnair007/chessMoveGuesser.git
cd chessMoveGuesser
```

Build & Run

- From inside the project directory, run:
- Also modify the application.properties and commands to make changes to server port and board size as needed.

```bash
docker build -t move_guesser . 
docker run -p 8080:8080 move_guesser
```
