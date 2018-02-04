package com.howettl.sudokusolver.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import com.howettl.sudokusolver.R
import com.howettl.sudokusolver.model.Position
import com.howettl.sudokusolver.model.Puzzle

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
        value?.entries?.forEach { position, entry ->
            getEntryView(position).setText(if (entry.isPopulated) entry.number.toString() else "")
        }
        field = value
    }

    private fun getEntryView(position: Position) =
            findViewById<EditText>(resources.getIdentifier("entry${position.row}${position.col}", "id", context.packageName))
}