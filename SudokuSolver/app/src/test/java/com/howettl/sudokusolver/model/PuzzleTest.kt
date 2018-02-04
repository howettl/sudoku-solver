package com.howettl.sudokusolver.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by Lee on 2018-02-04.
 * SudokuSolver
 */
class PuzzleTest {

    private val unsolvablePuzzle: MutableMap<Position, Entry> =
            mutableMapOf(
                    Position(0, 0) to Entry(3, true),
                    Position(0, 1) to Entry(0, false),
                    Position(0, 2) to Entry(2, false),
                    Position(0, 3) to Entry(0, false),
                    Position(0, 4) to Entry(0, false),
                    Position(0, 5) to Entry(0, false),
                    Position(0, 6) to Entry(0, false),
                    Position(0, 7) to Entry(0, false),
                    Position(0, 8) to Entry(0, false),
                    Position(1, 0) to Entry(0, false),
                    Position(1, 1) to Entry(5, false),
                    Position(1, 2) to Entry(0, false),
                    Position(1, 3) to Entry(0, false),
                    Position(1, 4) to Entry(0, false),
                    Position(1, 5) to Entry(0, false),
                    Position(1, 6) to Entry(4, false),
                    Position(1, 7) to Entry(0, false),
                    Position(1, 8) to Entry(0, false),
                    Position(2, 0) to Entry(7, false),
                    Position(2, 1) to Entry(0, false),
                    Position(2, 2) to Entry(0, false),
                    Position(2, 3) to Entry(0, false),
                    Position(2, 4) to Entry(0, false),
                    Position(2, 5) to Entry(0, false),
                    Position(2, 6) to Entry(0, false),
                    Position(2, 7) to Entry(3, false),
                    Position(2, 8) to Entry(0, false),
                    Position(3, 0) to Entry(2, false),
                    Position(3, 1) to Entry(0, false),
                    Position(3, 2) to Entry(0, false),
                    Position(3, 3) to Entry(4, false),
                    Position(3, 4) to Entry(0, false),
                    Position(3, 5) to Entry(8, false),
                    Position(3, 6) to Entry(7, false),
                    Position(3, 7) to Entry(0, false),
                    Position(3, 8) to Entry(0, false),
                    Position(4, 0) to Entry(4, false),
                    Position(4, 1) to Entry(6, false),
                    Position(4, 2) to Entry(0, false),
                    Position(4, 3) to Entry(7, false),
                    Position(4, 4) to Entry(0, false),
                    Position(4, 5) to Entry(1, false),
                    Position(4, 6) to Entry(3, false),
                    Position(4, 7) to Entry(2, false),
                    Position(4, 8) to Entry(8, false),
                    Position(5, 0) to Entry(8, false),
                    Position(5, 1) to Entry(0, false),
                    Position(5, 2) to Entry(7, false),
                    Position(5, 3) to Entry(0, false),
                    Position(5, 4) to Entry(0, false),
                    Position(5, 5) to Entry(0, false),
                    Position(5, 6) to Entry(1, false),
                    Position(5, 7) to Entry(0, false),
                    Position(5, 8) to Entry(0, false),
                    Position(6, 0) to Entry(5, false),
                    Position(6, 1) to Entry(0, false),
                    Position(6, 2) to Entry(1, false),
                    Position(6, 3) to Entry(0, false),
                    Position(6, 4) to Entry(7, false),
                    Position(6, 5) to Entry(4, false),
                    Position(6, 6) to Entry(9, false),
                    Position(6, 7) to Entry(0, false),
                    Position(6, 8) to Entry(3, false),
                    Position(7, 0) to Entry(0, false),
                    Position(7, 1) to Entry(0, false),
                    Position(7, 2) to Entry(0, false),
                    Position(7, 3) to Entry(9, false),
                    Position(7, 4) to Entry(2, false),
                    Position(7, 5) to Entry(5, false),
                    Position(7, 6) to Entry(8, false),
                    Position(7, 7) to Entry(0, false),
                    Position(7, 8) to Entry(7, false),
                    Position(8, 0) to Entry(9, false),
                    Position(8, 1) to Entry(0, false),
                    Position(8, 2) to Entry(0, false),
                    Position(8, 3) to Entry(0, false),
                    Position(8, 4) to Entry(0, false),
                    Position(8, 5) to Entry(3, false),
                    Position(8, 6) to Entry(0, false),
                    Position(8, 7) to Entry(0, false),
                    Position(8, 8) to Entry(2, false)
            )

    @Test
    fun puzzle_get_units_returns_correct_values() {
        val puzzle = Puzzle(unsolvablePuzzle)
        val returnedUnits = puzzle.getUnits(Position(3, 2))
        assertThat(returnedUnits.isNotEmpty(), `is`(true))
        val nestedEntryList = (0..8).map { row ->
            (0..8).map { col ->
                Position(row, col)
            }
        }
        val allPositions = nestedEntryList[0].union(nestedEntryList[1])
        val actualUnits = setOf(
                Position(3, 0),
                Position(3, 1),
                Position(3, 3),
                Position(3, 4),
                Position(3, 5),
                Position(3, 6),
                Position(3, 7),
                Position(3, 8),
                Position(0, 2),
                Position(1, 2),
                Position(2, 2),
                Position(4, 2),
                Position(5, 2),
                Position(6, 2),
                Position(7, 2),
                Position(8, 2),
                Position(3, 0),
                Position(3, 1),
                Position(4, 0),
                Position(4, 1),
                Position(4, 2),
                Position(5, 0),
                Position(5, 1),
                Position(5, 2)
        )
        val (units, notUnits) = allPositions.partition { actualUnits.contains(it) }
        units.forEach { assertThat(actualUnits.contains(it), `is`(true)) }
        notUnits.forEach { assertThat(actualUnits.contains(it), `is`(false))}
        actualUnits.forEach { assertThat(units.contains(it), `is`(true))}
        actualUnits.forEach { assertThat(notUnits.contains(it), `is`(false)) }
    }

}