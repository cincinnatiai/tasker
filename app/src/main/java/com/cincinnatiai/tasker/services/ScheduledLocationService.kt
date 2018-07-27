package com.cincinnatiai.tasker.services

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cincinnatiai.tasker.BuildConfig
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.gcm.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

private const val LOCATION_SERVICE_TAG = "tasker:location_service"

// Uses GCM library for service - library will determine whether to use jobscheduler for android 21+
class ScheduledLocationService : GcmTaskService(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private val TAG = ScheduledLocationService::class.java.simpleName

    private val googleApiClient by lazy {
        GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }
    private val locationRequest by lazy {
        LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            numUpdates = 1
            fastestInterval = 100
        }
    }

    // Called when the process has been killed b/c client app has been updated or play services has updated
    override fun onInitializeTasks() {
        super.onInitializeTasks()
        // Need to restart the service
        startLocationUpdates(this)
    }

    // Connects to google api client when task is run
    override fun onRunTask(params: TaskParams?): Int {
        googleApiClient.connect()
        return GcmNetworkManager.RESULT_SUCCESS
    }

    // The permission should be checked for in MainActivity
    @SuppressWarnings("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, object : LocationCallback() {

            override fun onLocationResult(location: LocationResult?) {
                super.onLocationResult(location)
                location?.apply {
                    Toast.makeText(this@ScheduledLocationService, "Location: ${toString()}", Toast.LENGTH_LONG).show()
                    // TODO 07/25/2018 Can persist either using sharedpreferences or sqllite
                }

            }
        }, null)
    }

    override fun onConnectionSuspended(p0: Int) {} // nop

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.w(TAG, "Connection failed")
    }

    companion object {

        @JvmStatic
        fun startLocationUpdates(context: Context) {
            val taskBuilder = PeriodicTask.Builder()
                    .setService(ScheduledLocationService::class.java)
                    .setPeriod(BuildConfig.TIME_INTERVAL)
                    .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                    .setRequiresCharging(false)
                    .setTag(LOCATION_SERVICE_TAG)
                    .setPersisted(true)
                    .build()
            GcmNetworkManager.getInstance(context).schedule(taskBuilder)
        }

        @JvmStatic
        fun cancelLocationUpdates(context: Context) {
            GcmNetworkManager
                    .getInstance(context)
                    .cancelTask(LOCATION_SERVICE_TAG, ScheduledLocationService::class.java)
        }
    }
}
