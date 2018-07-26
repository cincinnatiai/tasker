@file:JvmName("TaskerTextUtils")

package com.cincinnatiai.tasker.utils

import android.text.SpannableString
import android.text.Spanned
import android.view.View
import com.cincinnatiai.tasker.view.BareClickableSpan

// Performs a runnable when clicking on a target string
fun String.performOnTextClick(targetWord: String, onClickAction: () -> Unit) = SpannableString(this).apply {

    val clickableSpan = object : BareClickableSpan() {
        override fun onClick(p0: View?) {
            onClickAction()
        }
    }
    val indexToTarget = indexOf(targetWord)
    setSpan(clickableSpan, indexToTarget, indexToTarget + targetWord.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}