package com.cincinnatiai.tasker

import android.content.Context

interface MainContract {

    interface View {
        fun requestPermissions()
        fun showClickableHelloWorld()
        fun showFirstSuccessMessage()
        fun checkLocationsPermissions(): Boolean
        fun startLocationUpdates()
    }

    interface Presenter {
        fun start(view: View)
        fun helloClicked()
        fun detachView()
    }
}