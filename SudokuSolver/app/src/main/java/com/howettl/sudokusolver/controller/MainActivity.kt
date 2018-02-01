package com.howettl.sudokusolver.controller

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.howettl.sudokusolver.model.Puzzle
import com.howettl.sudokusolver.R
import com.howettl.sudokusolver.solver.BacktrackingSolver
import com.howettl.sudokusolver.solver.Solver
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : Activity() {

    private val solver: Solver = BacktrackingSolver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randomPuzzleButton.setOnClickListener {
            val client = OkHttpClient()
            val request = Request.Builder().url("https://sugoku.herokuapp.com/board?difficulty=easy").build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    System.out.println("Error getting puzzle: ${e?.localizedMessage}")
                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseJson = response?.body()?.string()
                    System.out.println(responseJson ?: "Response or body was null")
                    runOnUiThread {
                        val puzzle = Puzzle()
                        puzzle.setEntries(responseJson ?: return@runOnUiThread)
                        puzzleView.puzzle = puzzle
                    }
                }
            })
        }

        solvePuzzleButton.setOnClickListener {
            val puzzle = puzzleView.puzzle
            if (puzzle != null) {
                solver.solve(puzzle, { updatedPuzzle ->
                    puzzleView.puzzle = updatedPuzzle
                }, { finalPuzzle, solved ->
                    puzzleView.puzzle = finalPuzzle
                    Toast.makeText(this, if (solved) R.string.puzzle_solved_successfully else R.string.puzzle_not_solved, Toast.LENGTH_SHORT).show()
                })
            } else {
                Toast.makeText(this, R.string.no_puzzle, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
