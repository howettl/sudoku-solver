package com.howettl.sudokusolver.controller

import android.app.Activity
import android.os.Bundle
import com.howettl.sudokusolver.model.Puzzle
import com.howettl.sudokusolver.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randomPuzzleButton.setOnClickListener {
            val client = OkHttpClient()
            val request = Request.Builder().url("https://sugoku.herokuapp.com/board?difficulty=hard").build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    System.out.println("Error getting puzzle: ${e?.localizedMessage}")
                }

                override fun onResponse(call: Call?, response: Response?) {
                    val responseJson = response?.body()?.string()
                    System.out.println(responseJson ?: "Response or body was null")
                    runOnUiThread {
                        puzzleView.puzzle = Puzzle(responseJson
                                ?: return@runOnUiThread)
                    }
                }

            })
        }
    }
}
