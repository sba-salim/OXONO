# OXONO — Digital Board Game ❌⭕

OXONO is a strategic abstract board game implemented as a software project. The key challenge is enforcing Totem-based placement rules while detecting two orthogonal win conditions (symbol alignment vs color alignment) efficiently.

[Optional badges: build | tests | license]

Game overview
- Board: typically 6×6 (configurable)
- Goal: align 4 pieces of the same color OR 4 of the same symbol
- Core mechanic: Totem position restricts legal placement locations each turn

Technical highlights
- Language: Java (specify JDK version, e.g., JDK 17)
- Modules:
  - engine/ — core game logic (board, move validation, totem rules, win detection)
  - ai/ — (if any) players / strategies
  - cli/ or ui/ — interface layer (if present)
- Algorithms: optimized incremental win detection after each move (explain briefly below)

Quick start (Java / CLI)
Prerequisites
- JDK 17+
- Maven

Build & run
```bash
# Example using Maven
mvn -q package
java -jar target/oxono-1.0.jar
```

Or run from IDE: import as Maven/Gradle project and run the main class in `cli` or `app`.

Example play / rules summary
1. Each turn: move the Totem, then place a piece constrained by Totem geometry.
2. A valid win is 4-in-a-row of same color OR same symbol (rows, columns, diagonals if used).
3. Move validation enforces Totem-based geometric constraints and occupancy rules.

Engine notes & complexity
- Board represented as a 2D array with compact cell objects containing {symbol, color}.
- Win detection: after a placement, run directional checks (up/down/left/right/diags) starting at the placed cell — O(k) per direction where k ≤ board size. Incremental caching planned to reduce repeated work.

Testing
- Unit tests: describe how to execute (e.g., `mvn test`)
- Add test cases for edge cases: totem on border, near-win overlapping sequences, simultaneous multi-win conditions.

Roadmap / Improvements
- Determine and correct bugs with the bot-opponent and the modifiable size that doesn'it always work proprely
- Add AI opponent(s) with heuristics / search
- Add networked multiplayer and move history export
- Add more configuration options for board size & win length

Contributing & License
- Please open issues for suggestions or bugs.
- Add a LICENSE (MIT suggested).
