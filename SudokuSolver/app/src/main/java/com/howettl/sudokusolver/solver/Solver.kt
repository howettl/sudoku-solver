package com.howettl.sudokusolver.solver

import com.howettl.sudokusolver.model.Puzzle

/**
 * Created by Lee on 2018-01-31.
 * SudokuSolver
 */
interface Solver {
    fun solve(newPuzzle: Puzzle, updateListener: (Puzzle) -> Unit, completionListener: (Puzzle, Boolean) -> Unit)
}