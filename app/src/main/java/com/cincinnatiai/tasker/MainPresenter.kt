package com.cincinnatiai.tasker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

private const val REQUIRED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

// Handles user interaction and logic for MainView
class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null
    private var hasStartedBackgroundTask = false

    // sets the view and begins the first task
    override fun start(view: MainContract.View) {
        this.view = view
        view.showClickableHelloWorld()
    }

    // Handles a click on `Hello` in `Hello World!`; check permission first, start job next
    override fun helloClicked(context: Context) {
        view?.apply {
            showFirstSuccessMessage()
            if (!checkLocationsPermissions(context)) {
                requestPermissions()
            } else {
                startJob()
                hasStartedBackgroundTask = true
            }
        }
    }

    // Checks for locations permissions
    override fun checkLocationsPermissions(context: Context) =
            ActivityCompat.checkSelfPermission(context, REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED

    // Removes the view in case the process is killed
    override fun detachView() {
        view = null
    }
}