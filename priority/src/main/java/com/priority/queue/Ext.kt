package com.priority.queue

import android.app.Activity

/**
 * Time:2022/3/12 11:00 AM
 * Author: eastern
 * Description:
 */
internal fun checkActivityValid(activity: Activity?): Boolean {
    if (activity == null) {
        return false
    }
    return if (activity.isFinishing) {
        false
    } else !activity.isDestroyed
}

internal val TAG = "PriorityDialogQueue"

internal const val ERR_ACTIVITY_INVALID = -2