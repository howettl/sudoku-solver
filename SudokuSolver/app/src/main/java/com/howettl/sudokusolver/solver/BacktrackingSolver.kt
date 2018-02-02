package com.howettl.sudokusolver.solver

import android.os.Handler
import android.os.Looper
import com.howettl.sudokusolver.model.Entry
import com.howettl.sudokusolver.model.Position
import com.howettl.sudokusolver.model.Puzzle
import java.util.stream.Collectors.toSet

/**
 * Created by Lee on 2018-01-31.
 * SudokuSolver
 */
class BacktrackingSolver: Solver {
    private var originalEntries: Map<Position, Entry>
    private var currentEntries: MutableMap<Position, Entry>

    init {
        originalEntries = mapOf()
        currentEntries = mutableMapOf()
    }

    override fun solve(puzzle: Puzzle, updateListener: (Puzzle) -> Unit, completionListener: (Puzzle, Boolean) -> Unit) {
        Thread({
            if (puzzle.entries == null) {
                notifyCompletionListener(puzzle, false, completionListener)
                return@Thread
            }
            originalEntries = puzzle.entries ?: return@Thread
            currentEntries = puzzle.entries?.toMutableMap() ?: return@Thread

            if (getUnpopulatedPositions().isEmpty()) {
                notifyCompletionListener(puzzle, true, completionListener)
                return@Thread
            }
            doSimpleEntries()
            val updatedPuzzle = Puzzle()
            updatedPuzzle.entries = currentEntries
            notifyUpdateListener(updatedPuzzle, updateListener)
            if (getUnpopulatedPositions().isEmpty()) {
                val finishedPuzzle = Puzzle()
                finishedPuzzle.entries = currentEntries
                notifyCompletionListener(finishedPuzzle, true, completionListener)
                return@Thread
            }
            doSearch(updateListener)
            val finishedPuzzle = Puzzle()
            finishedPuzzle.entries = currentEntries
            notifyCompletionListener(finishedPuzzle, false, completionListener)
        }).run()
    }

    private fun notifyUpdateListener(puzzle: Puzzle, updateListener: (Puzzle) -> Unit) {
        Handler(Looper.getMainLooper()).post { updateListener(puzzle) }
    }

    private fun notifyCompletionListener(puzzle: Puzzle, solved: Boolean, completionListener: (Puzzle, Boolean) -> Unit) {
        Handler(Looper.getMainLooper()).post { completionListener(puzzle, solved) }
    }

    private fun doSimpleEntries() {
        var knownUnpopulatedPositions = getUnpopulatedPositionsWithSingleCandidate()
        while (knownUnpopulatedPositions.isNotEmpty()) {
            knownUnpopulatedPositions.keys.forEach { position ->
                currentEntries[position] = Entry(getPossibleValues(position).first(), false)
            }
            val updatedPuzzle = Puzzle()
            updatedPuzzle.entries = currentEntries
            knownUnpopulatedPositions = getUnpopulatedPositionsWithSingleCandidate()
        }
    }

    private fun doSearch(updateListener: (Puzzle) -> Unit) {
        // TODO implement
    }

    private fun getUnits(position: Position): Set<Position> {
        val positions = hashSetOf<Position>()
        (0 .. 8).forEach { colIndex ->
            positions.add(Position(position.row, colIndex))
        }
        (0 .. 8).forEach { rowIndex ->
            positions.add(Position(rowIndex, position.col))
        }
        when (position.row) {
            in 0..2 -> positions.addAll((0..2).map { Position(it, position.col) })
            in 3..5 -> positions.addAll((3..5).map { Position(it, position.col) })
            in 6..8 -> positions.addAll((6..8).map { Position(it, position.col) })
            else -> return@getUnits hashSetOf()
        }
        when (position.col) {
            in 0..2 -> positions.addAll((0..2).map { Position(position.row, it) })
            in 3..5 -> positions.addAll((3..5).map { Position(position.row, it) })
            in 6..8 -> positions.addAll((6..8).map { Position(position.row, it) })
            else -> return@getUnits hashSetOf()
        }
        positions.remove(position)
        return positions
    }

    private fun getPossibleValues(position: Position): Set<Int> {
        val populatedUnits = getUnits(position).filter { currentEntries[it]?.isPopulated() ?: false }
        return (1..9).filterNot { populatedUnits.map { currentEntries[it]?.number }.contains(it) }.toSet()
    }

    private fun getUnpopulatedPositions() = currentEntries.filterValues { !it.isPopulated() }.keys

    private fun getUnpopulatedPositionsWithSingleCandidate() =
            currentEntries.filterKeys { currentEntries[it]?.isPopulated() == false && getPossibleValues(it).size == 1 }
}
