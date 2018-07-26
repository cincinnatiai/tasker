package com.cincinnatiai.tasker.view

import android.text.TextPaint
import android.text.style.ClickableSpan

// Clickable span without any underline
abstract class BareClickableSpan : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint?) {
        super.updateDrawState(ds)
        ds?.isUnderlineText = false
    }
}