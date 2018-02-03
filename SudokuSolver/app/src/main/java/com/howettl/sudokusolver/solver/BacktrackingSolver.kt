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
    private var puzzle: Puzzle = Puzzle()

    override fun solve(newPuzzle: Puzzle, updateListener: (Puzzle) -> Unit, completionListener: (Puzzle, Boolean) -> Unit) {
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
            if (puzzle.getUnpopulatedPositions().isEmpty()) {
                notifyCompletionListener(puzzle, true, completionListener)
                return@Thread
            }
            puzzle = doSearch(puzzle, updateListener)
            notifyCompletionListener(puzzle, puzzle.getUnpopulatedPositions().isEmpty(), completionListener)
        }).run()
    }

    private fun notifyUpdateListener(puzzle: Puzzle, updateListener: (Puzzle) -> Unit) {
        Handler(Looper.getMainLooper()).post { updateListener(puzzle) }
    }

    private fun notifyCompletionListener(puzzle: Puzzle, solved: Boolean, completionListener: (Puzzle, Boolean) -> Unit) {
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

    private fun doSearch(puzzle: Puzzle, updateListener: (Puzzle) -> Unit): Puzzle {
        return puzzle
    }
}
