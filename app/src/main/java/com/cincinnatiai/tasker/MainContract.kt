package com.cincinnatiai.tasker

import android.content.Context

interface MainContract {

    interface View {
        fun requestPermissions()
        fun showClickableHelloWorld()
        fun showFirstSuccessMessage()
        fun startJob()
    }

    interface Presenter {
        fun start(view: View)
        fun helloClicked(context: Context)
        fun checkLocationsPermissions(context: Context): Boolean
        fun detachView()
    }
}