package com.priority.queue.log

import android.util.Log

/**
 * Time:2022/3/21 9:50 AM
 * Author: eastern
 * Description:
 */
class DefaultLog : IQueueLog {
    private val TAG = "QueueTag"
    override fun i(tag: String, message: String?) {
        Log.i(TAG, "$message")
    }

    override fun e(tag: String, message: String?, t: Throwable?) {
        Log.e(TAG, "$message", t)
    }

    override fun e(tag: String, message: String?) {
        Log.e(TAG, "$message")
    }
}