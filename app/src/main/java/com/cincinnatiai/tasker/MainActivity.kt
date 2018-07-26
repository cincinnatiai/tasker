package com.cincinnatiai.tasker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val targetString by lazy { getString(R.string.main_target_word) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setTextAndListener() {

    }
}
