# OXONO - Digital Board Game ❌⭕

## Project Overview
This project is a digital implementation of **OXONO**, a strategic abstract board game.
The goal was to translate complex board rules (totem movements, symbol/color alignment constraints) into a rigorous software application.

It demonstrates my ability to handle **Game State Management** and implement **Validation Algorithms**.

## Game Rules & Logic
OXONO is a strategy game played on a 6x6 grid (or adapted size).
* **Goal:** Align 4 pieces of the same color OR same symbol.
* **Core Mechanic:** Players must move a "Totem" to dictate where they can place their piece.
* **Challenge:** The algorithm must validate two distinct win conditions simultaneously (Symbol alignment vs Color alignment).

## Key Technical Features
* **State Management:** Keeping track of the board state, player turns, and totem positions.
* **Move Validation:** Ensuring every move respects the geometric constraints of the Totem.
* **Win Detection Algorithm:** optimized checking of rows, columns (and diagonals if applicable) after every turn to detect a winner instantly.

