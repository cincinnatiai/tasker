package com.cincinnatiai.tasker

import android.Manifest
import android.support.annotation.VisibleForTesting

private const val REQUIRED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

// Handles user interaction and logic for MainView
class MainPresenter : MainContract.Presenter {

    @VisibleForTesting var view: MainContract.View? = null
    @VisibleForTesting var hasStartedBackgroundTask = false

    // sets the view and begins the first task
    override fun start(view: MainContract.View) {
        this.view = view
        view.showClickableHelloWorld()
    }

    // Handles a click on `Hello` in `Hello World!`; check permission first, start job next
    override fun helloClicked() {
        view?.apply {
            showFirstSuccessMessage()
            if (!checkLocationsPermissions()) {
                requestPermissions()
            } else {
                startLocationUpdates()
                hasStartedBackgroundTask = true
            }
        }
    }

    // Removes the view in case the process is killed
    override fun detachView() {
        view = null
    }
}