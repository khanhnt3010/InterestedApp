package com.ntkhanh.myinterestedapp

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */

@Singleton
open class AppExcutors(private val mDiskIO: Executor, private val mNetworkIO: Executor, private val mMainThread: Executor) {

    @Inject
    constructor() : this(
            Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(3),
            MainThreadExecutor()
    )

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}