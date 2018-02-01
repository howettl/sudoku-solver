package com.howettl.sudokusolver.model

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
class Puzzle {
    var entries: Map<Position, Entry>? = null

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
}