package com.howettl.sudokusolver

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_puzzle.view.*

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
class PuzzleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_puzzle, this, true)
    }

    var puzzle: Puzzle? = null
    set(value) {
        value?.entries?.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, entry ->
                if (entry.number in 1..9) getEntryView(rowIndex, columnIndex).setText(entry.number.toString())
                else getEntryView(rowIndex, columnIndex).setText("")
            }
        }
        field = value
    }

    private fun getEntryView(rowIndex: Int, columnIndex: Int) =
            findViewById<EditText>(resources.getIdentifier("entry$rowIndex$columnIndex", "id", context.packageName))
}