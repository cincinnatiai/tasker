package com.cincinnatiai.tasker

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.text.method.LinkMovementMethod
import com.cincinnatiai.tasker.base.BaseCompatActivity
import com.cincinnatiai.tasker.services.ScheduledLocationService
import com.cincinnatiai.tasker.utils.performOnTextClick
import kotlinx.android.synthetic.main.activity_main.*


private const val REQUIRED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
private const val REQUEST_PERMISSION = 23

class MainActivity : BaseCompatActivity(), MainContract.View {

    // TODO 07/26/2018 Use dagger to inject if given more time
    private val presenter by lazy { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        presenter.start(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            when {
                grantResults.isEmpty() -> {
                    // Permissions were cancelled somehow was disrupted somehow
                    //TODO 07/25/2018 Do something other than nothing
                }
                grantResults.first() == PackageManager.PERMISSION_GRANTED -> // Permission granted
                    presenter.helloClicked(this)
                else -> // Permission was denied; Could have been from a `Don't ask again` checkbox
                    AlertDialog.Builder(this)
                            .setTitle(R.string.task_two_permission_denied_title)
                            .setMessage(R.string.task_two_permission_denied_message)
                            .setPositiveButton(R.string.enable, {_,_ ->
                                // Build intent that displays the App settings screen.
                                val intent = Intent().apply {
                                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                startActivity(intent)
                            })
                            .setNegativeButton(R.string.not_now, null)
                            .show()
            }
        }
    }

    override fun showClickableHelloWorld() {
        val helloWorld = getString(R.string.main_hello_world)
        val targetWord = getString(R.string.main_target_word)
        id_text_view.apply {
            movementMethod = LinkMovementMethod.getInstance()
            // Prevent highlight on text when tapped
            highlightColor = Color.TRANSPARENT

            // Set the listener via clickable span
            text = helloWorld.performOnTextClick(targetWord, {
                presenter.helloClicked(this@MainActivity)
            })
        }
    }

    // Displays first toast for success message
    override fun showFirstSuccessMessage() {
        showToast(R.string.task_1_success)
    }

    // Starts job using GCMtask extended service
    override fun startJob() {
        ScheduledLocationService.startLocationUpdates(this)
    }

    // Request permissions needed to check locations, this is done runtime on Android API 23+
    override fun requestPermissions() {
        val needsToProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSION)

        // Happens when the user has denied it previously
        if (needsToProvideRationale) {
            AlertDialog.Builder(this)
                    .setTitle(R.string.task_two_permission_title)
                    .setMessage(R.string.task_two_permission_rationale)
                    .setPositiveButton(R.string.enable, { _, _ ->
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
                    })
                    .setNegativeButton(R.string.not_now, null)
                    .show()
        } else {
            // Request permissions for the first time
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
        }
    }
}