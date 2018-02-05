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
                    Position(0, 2) to Entry(2, true),
                    Position(0, 3) to Entry(0, false),
                    Position(0, 4) to Entry(0, false),
                    Position(0, 5) to Entry(0, false),
                    Position(0, 6) to Entry(0, false),
                    Position(0, 7) to Entry(0, false),
                    Position(0, 8) to Entry(0, false),
                    Position(1, 0) to Entry(0, false),
                    Position(1, 1) to Entry(5, true),
                    Position(1, 2) to Entry(0, false),
                    Position(1, 3) to Entry(0, false),
                    Position(1, 4) to Entry(0, false),
                    Position(1, 5) to Entry(0, false),
                    Position(1, 6) to Entry(4, true),
                    Position(1, 7) to Entry(0, false),
                    Position(1, 8) to Entry(0, false),
                    Position(2, 0) to Entry(7, true),
                    Position(2, 1) to Entry(0, false),
                    Position(2, 2) to Entry(0, false),
                    Position(2, 3) to Entry(0, false),
                    Position(2, 4) to Entry(0, false),
                    Position(2, 5) to Entry(0, false),
                    Position(2, 6) to Entry(0, false),
                    Position(2, 7) to Entry(3, true),
                    Position(2, 8) to Entry(0, false),
                    Position(3, 0) to Entry(2, true),
                    Position(3, 1) to Entry(0, false),
                    Position(3, 2) to Entry(0, false),
                    Position(3, 3) to Entry(4, true),
                    Position(3, 4) to Entry(0, false),
                    Position(3, 5) to Entry(8, true),
                    Position(3, 6) to Entry(7, true),
                    Position(3, 7) to Entry(0, false),
                    Position(3, 8) to Entry(0, false),
                    Position(4, 0) to Entry(4, true),
                    Position(4, 1) to Entry(6, true),
                    Position(4, 2) to Entry(0, false),
                    Position(4, 3) to Entry(7, true),
                    Position(4, 4) to Entry(0, false),
                    Position(4, 5) to Entry(1, true),
                    Position(4, 6) to Entry(3, true),
                    Position(4, 7) to Entry(2, true),
                    Position(4, 8) to Entry(8, true),
                    Position(5, 0) to Entry(8, true),
                    Position(5, 1) to Entry(0, false),
                    Position(5, 2) to Entry(7, true),
                    Position(5, 3) to Entry(0, false),
                    Position(5, 4) to Entry(0, false),
                    Position(5, 5) to Entry(0, false),
                    Position(5, 6) to Entry(1, true),
                    Position(5, 7) to Entry(0, false),
                    Position(5, 8) to Entry(0, false),
                    Position(6, 0) to Entry(5, true),
                    Position(6, 1) to Entry(0, false),
                    Position(6, 2) to Entry(1, true),
                    Position(6, 3) to Entry(0, false),
                    Position(6, 4) to Entry(7, true),
                    Position(6, 5) to Entry(4, true),
                    Position(6, 6) to Entry(9, true),
                    Position(6, 7) to Entry(0, false),
                    Position(6, 8) to Entry(3, true),
                    Position(7, 0) to Entry(0, false),
                    Position(7, 1) to Entry(0, false),
                    Position(7, 2) to Entry(0, false),
                    Position(7, 3) to Entry(9, true),
                    Position(7, 4) to Entry(2, true),
                    Position(7, 5) to Entry(5, true),
                    Position(7, 6) to Entry(8, true),
                    Position(7, 7) to Entry(0, false),
                    Position(7, 8) to Entry(7, true),
                    Position(8, 0) to Entry(9, true),
                    Position(8, 1) to Entry(0, false),
                    Position(8, 2) to Entry(0, false),
                    Position(8, 3) to Entry(0, false),
                    Position(8, 4) to Entry(0, false),
                    Position(8, 5) to Entry(3, true),
                    Position(8, 6) to Entry(0, false),
                    Position(8, 7) to Entry(0, false),
                    Position(8, 8) to Entry(2, true)
            )

    @Test
    fun puzzle_get_units_returns_correct_values() {
        val puzzle = Puzzle(unsolvablePuzzle)
        val returnedUnits = puzzle.getUnits(Position(3, 2))
        assertThat(returnedUnits.isNotEmpty(), `is`(true))
        val allPositions = getAllPositions()
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
        assertThat(units.size == returnedUnits.size, `is`(true))
        units.forEach { assertThat(returnedUnits.contains(it), `is`(true)) }
        notUnits.forEach { assertThat(returnedUnits.contains(it), `is`(false))}
        returnedUnits.forEach { assertThat(units.contains(it), `is`(true))}
        returnedUnits.forEach { assertThat(notUnits.contains(it), `is`(false)) }
    }

    @Test
    fun puzzle_get_unpopulated_positions() {
        val puzzle = Puzzle(unsolvablePuzzle)
        val returnedUnpopulatedPositions = puzzle.getUnpopulatedPositions()
        val allPositions = getAllPositions()
        val actualUnpopulatedPositions = setOf(
                Position(0, 1),
                Position(0, 3),
                Position(0, 4),
                Position(0, 5),
                Position(0, 6),
                Position(0, 7),
                Position(0, 8),
                Position(1, 0),
                Position(1, 2),
                Position(1, 3),
                Position(1, 4),
                Position(1, 5),
                Position(1, 7),
                Position(1, 8),
                Position(2, 1),
                Position(2, 2),
                Position(2, 3),
                Position(2, 4),
                Position(2, 5),
                Position(2, 6),
                Position(2, 8),
                Position(3, 1),
                Position(3, 2),
                Position(3, 4),
                Position(3, 7),
                Position(3, 8),
                Position(4, 2),
                Position(4, 4),
                Position(5, 1),
                Position(5, 3),
                Position(5, 4),
                Position(5, 5),
                Position(5, 7),
                Position(5, 8),
                Position(6, 1),
                Position(6, 3),
                Position(6, 7),
                Position(7, 0),
                Position(7, 1),
                Position(7, 2),
                Position(7, 7),
                Position(8, 1),
                Position(8, 2),
                Position(8, 3),
                Position(8, 4),
                Position(8, 6),
                Position(8, 7)
        )
        val (positions, notPositions) = allPositions.partition { actualUnpopulatedPositions.contains(it) }
        assertThat(positions.size == returnedUnpopulatedPositions.size, `is`(true))
        positions.forEach { assertThat(returnedUnpopulatedPositions.contains(it), `is`(true)) }
        notPositions.forEach { assertThat(returnedUnpopulatedPositions.contains(it), `is`(false))}
        returnedUnpopulatedPositions.forEach { assertThat(positions.contains(it), `is`(true))}
        returnedUnpopulatedPositions.forEach { assertThat(notPositions.contains(it), `is`(false)) }
    }

    @Test
    fun puzzle_get_unpopulated_positions_with_single_candidate() {
        val puzzle = Puzzle(unsolvablePuzzle)
        val returnedPositions = puzzle.getUnpopulatedPositionsWithSingleCandidate()
        val allPositions = getAllPositions()
        val actualPositions = setOf(
                Position(6, 7),
                Position(7, 0)
        )
        val (positions, notPositions) = allPositions.partition { actualPositions.contains(it) }
        assertThat(positions.size == returnedPositions.size, `is`(true))
        positions.forEach { assertThat(returnedPositions.contains(it), `is`(true)) }
        notPositions.forEach { assertThat(returnedPositions.contains(it), `is`(false))}
        returnedPositions.forEach { assertThat(positions.contains(it.key), `is`(true))}
        returnedPositions.forEach { assertThat(notPositions.contains(it.key), `is`(false)) }
    }

    @Test
    fun puzzle_get_unpopulated_positions_with_no_candidates() {
        val puzzleCopy = HashMap<Position, Entry>()
        puzzleCopy.putAll(unsolvablePuzzle)
        puzzleCopy[Position(7, 7)] = Entry(6, false)
        puzzleCopy[Position(0, 1)] = Entry(1, false)
        puzzleCopy[Position(2, 1)] = Entry(6, false)

        val puzzle = Puzzle(puzzleCopy)
        val returnedPositions = puzzle.getUnpopulatedPositionsWithNoCandidates()
        val allPositions = getAllPositions()
        val actualPositions = setOf(
                Position(1, 0),
                Position(6, 7),
                Position(7, 0)
        )
        val (positions, notPositions) = allPositions.partition { actualPositions.contains(it) }
        assertThat(positions.size == returnedPositions.size, `is`(true))
        positions.forEach { assertThat(returnedPositions.contains(it), `is`(true)) }
        notPositions.forEach { assertThat(returnedPositions.contains(it), `is`(false))}
        returnedPositions.forEach { assertThat(positions.contains(it.key), `is`(true))}
        returnedPositions.forEach { assertThat(notPositions.contains(it.key), `is`(false)) }
    }

    private fun getAllPositions() =
        (0..8).map { row ->
            (0..8).map { col ->
                Position(row, col)
            }
        }.flatten()

}