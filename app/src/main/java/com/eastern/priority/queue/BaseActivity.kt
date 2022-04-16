package com.eastern.priority.queue

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import com.priority.queue.queue.ActivityDialogQueue
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.IScene

/**
 * Time:2022/3/13 10:35 AM
 * Author: eastern
 * Description:
 */
open class BaseActivity : FragmentActivity(), IScene {
    protected val handler = Handler(Looper.getMainLooper())
    override fun currentScene(): Class<out IScene> {
        return GlobalScene::class.java
    }

    fun postDelayed(runnable: Runnable, delay: Long) {
        if (ActivityDialogQueue.empty()) {
            return
        }
        handler.postDelayed(runnable, delay)
    }
}