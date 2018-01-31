package com.howettl.sudokusolver

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by Lee on 2018-01-30.
 * SudokuSolver
 */
class PuzzleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, var puzzle: Puzzle? = null
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_puzzle, this, true)
    }
}