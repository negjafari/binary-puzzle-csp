# CSP binary puzzle

Solving binary puzzle by backtracking using forward checking and improve code by MVR heuristic.

## Overview

Binary Puzzles, also known as binario puzzles, are solved by filling a table with 'A' and 'B' characters while satisfying various constraints. This project models the problem as a Constraint Satisfaction Problem (CSP) and utilizes backtracking with forward checking and the Minimum Remaining Values (MRV) heuristic to efficiently find solutions.

## Features

- Constraint satisfaction problem modeling
- Backtracking algorithm with forward checking
- MRV heuristic for variable selection

## CSP Modeling

### Variables

The variables in the BinaryPuzzle Solver represent the unfilled places in the puzzle table. In an `n x n` table with `m` filled places, the number of variables is `n^2 - m`. Each variable can take the values 'A' or 'B', representing the characters to be filled.

### Domain

The domain of each variable is whether it can be 'A' or 'B'.

### Constraints

1. **Equal Distribution Constraint:**
   - In each row and column, the number of 'A' and 'B' characters should be equal. In a general `n x n` table, in each row and column, the number of 'A' and 'B' characters should be `n/2`.

2. **Adjacent Identical Characters Constraint:**
   - More than two similar characters cannot be adjacent to each other in a row or a column.

3. **Uniqueness Constraint:**
   - Rows and columns must be unique, ensuring that no two rows or columns are identical.

### Algorithm

The solver utilizes backtracking with forward checking to explore possible assignments for variables. The Minimum Remaining Values (MRV) heuristic is employed for variable selection, ensuring the most constrained variable is selected first. Forward checking is used to prune the search space by immediately discarding inconsistent assignments.

## Usage
Execute `Main.java` located in `Binairo-Puzzle-master/Main.java`.

**Sample Inputs:**
Sample inputs are available in `inputs/`.


