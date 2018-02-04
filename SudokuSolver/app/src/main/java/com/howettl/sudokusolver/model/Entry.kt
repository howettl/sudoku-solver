package com.howettl.sudokusolver.model

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
data class Entry(val number: Int, var isClue: Boolean) {
    val isPopulated = number in 1..9
}