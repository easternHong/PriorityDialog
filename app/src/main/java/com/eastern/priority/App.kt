package com.eastern.priority

import android.app.Application
import com.priority.queue.queue.ActivityDialogQueue

/**
 * Time:2022/3/15 2:34 PM
 * Author: eastern
 * Description:
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        ActivityDialogQueue.init(this)
        GenerateDialog
    }
}