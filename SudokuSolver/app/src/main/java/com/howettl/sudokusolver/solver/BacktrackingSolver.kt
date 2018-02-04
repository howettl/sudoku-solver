package com.howettl.sudokusolver.solver

import android.os.Handler
import android.os.Looper
import com.howettl.sudokusolver.model.Entry
import com.howettl.sudokusolver.model.Puzzle

/**
 * Created by Lee on 2018-01-31.
 * SudokuSolver
 */
class BacktrackingSolver: Solver {
    private var puzzle: Puzzle = Puzzle()

    override fun solve(newPuzzle: Puzzle, updateListener: (Puzzle) -> Unit, completionListener: (Puzzle?, Boolean) -> Unit) {
        Thread({
            puzzle = newPuzzle

            if (puzzle.entries == null) {
                notifyCompletionListener(puzzle, false, completionListener)
                return@Thread
            }

            if (puzzle.getUnpopulatedPositions().isEmpty()) {
                notifyCompletionListener(puzzle, true, completionListener)
                return@Thread
            }
            puzzle = doSimpleEntries(puzzle)
            notifyUpdateListener(puzzle, updateListener)
            if (puzzle.isSolved) {
                notifyCompletionListener(puzzle, true, completionListener)
                return@Thread
            }
            val completedPuzzle = doSearch(puzzle, updateListener)
            notifyCompletionListener(completedPuzzle, completedPuzzle?.isSolved ?: false, completionListener)
        }).run()
    }

    private fun notifyUpdateListener(puzzle: Puzzle, updateListener: (Puzzle) -> Unit) {
        Handler(Looper.getMainLooper()).post { updateListener(puzzle) }
    }

    private fun notifyCompletionListener(puzzle: Puzzle?, solved: Boolean, completionListener: (Puzzle?, Boolean) -> Unit) {
        Handler(Looper.getMainLooper()).post { completionListener(puzzle, solved) }
    }

    private fun doSimpleEntries(puzzle: Puzzle): Puzzle {
        var knownUnpopulatedPositions = puzzle.getUnpopulatedPositionsWithSingleCandidate()
        while (knownUnpopulatedPositions.isNotEmpty()) {
            knownUnpopulatedPositions.keys.forEach { position ->
                puzzle.entries?.put(position, Entry(puzzle.getPossibleValues(position).first(), false))
            }
            knownUnpopulatedPositions = puzzle.getUnpopulatedPositionsWithSingleCandidate()
        }
        return puzzle
    }

    private fun doSearch(searchPuzzle: Puzzle, updateListener: (Puzzle) -> Unit): Puzzle? {
        if (searchPuzzle.isSolved) return searchPuzzle
        if (searchPuzzle.isUnsolvable) return null

        val (position, candidates) = searchPuzzle.getUnpopulatedPosition()
        if (candidates.isEmpty()) return null
        candidates.forEach { candidate ->
            searchPuzzle.entries?.put(position, Entry(candidate, false))
            notifyUpdateListener(searchPuzzle, updateListener)
            val updatedPuzzle = doSearch(searchPuzzle, updateListener)
            if (updatedPuzzle?.isSolved == true) return updatedPuzzle
        }
        return null
    }
}
