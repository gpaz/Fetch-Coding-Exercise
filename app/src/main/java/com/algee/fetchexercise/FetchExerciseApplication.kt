package com.algee.fetchexercise

import android.app.Application

/**
 * We can use initializers in place of setting of long-living elements here in the
 * [onCreate] call.  But for some things we still might need to have this available
 * to accomplish, such as reacting to [onTrimMemory] and [onLowMemory] for example.
 */
class FetchExerciseApplication : Application()