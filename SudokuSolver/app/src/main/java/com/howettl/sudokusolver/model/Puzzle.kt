package com.howettl.sudokusolver.model

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
class Puzzle(json: String) {
    val entries: List<List<Entry>>
    init {
        try {
            val jsonObject = JSONObject(json)
            val board = jsonObject.getJSONArray("board")
            val parsedEntries: MutableList<List<Entry>> = arrayListOf()
            for (i: Int in 0 until board.length()) {
                val row = board.getJSONArray(i)
                val entryRow: MutableList<Entry> = arrayListOf()
                (0 until row.length())
                        .map { row.getInt(it) }
                        .forEach { entryRow.add(Entry(it, it in 1..9)) }
                parsedEntries.add(entryRow)
            }
            entries = parsedEntries
        } catch (e: JSONException) {
            Log.e("Puzzle", "Error marshalling JSON into Puzzle", e)
            TODO()
        }
    }
}