package com.howettl.sudokusolver

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
class Puzzle(val entries: List<List<Entry>>) {
    companion object {
        fun fromRawEntries(rawEntries: List<List<Int>>) = Puzzle(rawEntries.map { row -> row.map { number -> Entry(number, number in 1..9) } })
    }
}