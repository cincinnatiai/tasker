@file:JvmName("PlayUtils")

package com.cincinnatiai.tasker.utils

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil

fun Context.hasGooglePlayServices() = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS