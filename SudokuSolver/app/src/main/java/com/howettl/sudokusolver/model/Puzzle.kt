package com.howettl.sudokusolver.model

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
class Puzzle(var entries: MutableMap<Position, Entry>?) {
    constructor(): this(null)

    fun setEntries(json: String) {
        try {
            val jsonObject = JSONObject(json)
            val board = jsonObject.getJSONArray("board")
            val parsedEntries: MutableMap<Position, Entry> = mutableMapOf()
            for (row: Int in 0 until board.length()) {
                val rowArray = board.getJSONArray(row)
                for (col: Int in 0 until rowArray.length()) {
                    val number = rowArray.getInt(col)
                    parsedEntries[Position(row, col)] = Entry(number, true)
                }
            }
            entries = parsedEntries
        } catch (e: JSONException) {
            Log.e("Puzzle", "Error marshalling JSON into Puzzle", e)
            TODO()
        }
    }

    val isSolved
    get() = getUnpopulatedPositions().isEmpty()

    val isUnsolvable
    get() = getUnpopulatedPositionsWithNoCandidates().isNotEmpty()

    internal fun getUnits(position: Position): Set<Position> {
        val positions = hashSetOf<Position>()
        (0 .. 8).forEach { colIndex ->
            positions.add(Position(position.row, colIndex))
        }
        (0 .. 8).forEach { rowIndex ->
            positions.add(Position(rowIndex, position.col))
        }
        val rowRange: IntRange = when (position.row) {
            in 0..2 -> 0..2
            in 3..5 -> 3..5
            in 6..8 -> 6..8
            else -> return@getUnits hashSetOf()
        }
        val colRange: IntRange = when (position.col) {
            in 0..2 -> 0..2
            in 3..5 -> 3..5
            in 6..8 -> 6..8
            else -> return@getUnits hashSetOf()
        }
        for (row: Int in rowRange) { colRange.mapTo(positions) { Position(row, it) } }
        positions.remove(position)
        return positions
    }

    data class PositionWithCandidates(val position: Position, val candidates: Set<Int>)

    fun getUnpopulatedPosition(): PositionWithCandidates {
        val position = getUnpopulatedPositions().first()
        return PositionWithCandidates(position, getPossibleValues(position))
    }

    fun getPossibleValues(position: Position): Set<Int> {
        val populatedUnits = getUnits(position).filter { entries?.get(it)?.isPopulated ?: false }
        return (1..9).filterNot { populatedUnits.map { entries?.get(it)?.number }.contains(it) }.toSet()
    }

    fun getUnpopulatedPositions() = entries?.filterValues { !it.isPopulated }?.keys ?: setOf()

    fun getUnpopulatedPositionsWithSingleCandidate() =
            entries?.filterKeys { entries?.get(it)?.isPopulated == false && getPossibleValues(it).size == 1 } ?: mapOf()

    internal fun getUnpopulatedPositionsWithNoCandidates() =
            entries?.filterKeys { entries?.get(it)?.isPopulated == false && getPossibleValues(it).isEmpty() } ?: mapOf()
}