package com.cincinnatiai.tasker.base

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

// base class activities should extend to maintain simple pattern
open class BaseCompatActivity : AppCompatActivity() {

    fun showToast(@StringRes stringResource: Int) {
        Toast.makeText(this, stringResource, Toast.LENGTH_LONG).show()
    }
}