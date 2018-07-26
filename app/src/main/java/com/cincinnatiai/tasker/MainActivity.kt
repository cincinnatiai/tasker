package com.cincinnatiai.tasker

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.cincinnatiai.tasker.base.BaseCompatActivity
import com.cincinnatiai.tasker.utils.performOnTextClick
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTextAndListener()
    }

    // Sets hello world and the click listener for Task 1
    private fun setTextAndListener() {
        val helloWorld = getString(R.string.main_hello_world)
        val targetWord = getString(R.string.main_target_word)
        id_text_view.apply {
            movementMethod = LinkMovementMethod.getInstance()
            // Prevent highlight on text when tapped
            highlightColor = Color.TRANSPARENT
            text = helloWorld.performOnTextClick(targetWord, {
                showToast(R.string.task_1_success)
            })
        }
    }
}