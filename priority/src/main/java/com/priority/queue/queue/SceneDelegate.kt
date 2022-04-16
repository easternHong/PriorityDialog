package com.priority.queue.queue

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.priority.queue.TAG
import com.priority.queue.scene.IScene
import com.priority.queue.scene.NoneScene

/**
 * Time:2022/3/10 2:54 PM
 * Author: eastern
 * Description:
 */

internal enum class SceneDelegate {
    INSTANCE;

    private val controller = Controller()
    private var isInit = false

    fun init(application: Application) {
        if (isInit) {
            Log.e(TAG, "has inited!")
            return
        }
        isInit = true
        application.registerComponentCallbacks(controller)
        application.registerActivityLifecycleCallbacks(controller)
    }

    fun currentActivity(): Activity? {
        return controller.currentActivity
    }

    private inner class Controller : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

        var currentActivity: Activity? = null
            private set(activity) {
                field = activity
                Log.i(TAG, "activity==set current activity: %s${field?.componentName?.className}")
            }

        private fun isCurrentActivity(activity: Activity?): Boolean {
            return activity === currentActivity
        }

        override fun onActivityStarted(activity: Activity) {
            Log.i(TAG, "onActivityStarted: %s${activity::class.simpleName}")
            currentActivity = activity
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            val scene = (activity as? IScene) ?: return
            if (activity !is FragmentActivity) return
            if (scene == NoneScene::class.java) {
                return
            }
            ActivityConsumer.create(scene)
        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.i(TAG, "onActivityDestroyed: %s${activity::class.simpleName}")
            if (isCurrentActivity(activity)) {
                currentActivity = null
            }
            val scene = (activity as? IScene) ?: return
            if (activity !is FragmentActivity) return
            if (scene == NoneScene::class.java) {
                return
            }
            ActivityConsumer.destroy(scene)
        }

        override fun onActivityResumed(activity: Activity) {
            Log.i(TAG, "onActivityResumed: %s${activity::class.simpleName}")
            currentActivity = activity
            val scene = (activity as? IScene) ?: return
            if (activity !is FragmentActivity) return
            if (scene == NoneScene::class.java) {
                return
            }
            ActivityConsumer.resume(scene)
        }

        override fun onActivityPaused(activity: Activity) {
            val scene = (activity as? IScene) ?: return
            if (activity !is FragmentActivity) return
            if (scene == NoneScene::class.java) {
                return
            }
            ActivityConsumer.pause(scene)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onConfigurationChanged(newConfig: Configuration) {}
        override fun onLowMemory() {}
        override fun onTrimMemory(level: Int) {}
    }
}