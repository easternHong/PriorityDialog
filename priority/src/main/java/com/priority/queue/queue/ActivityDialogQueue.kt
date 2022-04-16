package com.priority.queue.queue

import android.app.Application
import android.util.Log
import com.priority.queue.Priority
import com.priority.queue.scene.IScene
import com.priority.queue.task.BaseTask
import com.priority.queue.task.PriorityDialogTask

/**
 * Time:2022/3/14 11:22 AM
 * Author: eastern
 * Description:
 */
object ActivityDialogQueue {

    fun init(application: Application) {
        SceneDelegate.INSTANCE.init(application)
    }

    suspend fun enqueue(element: PriorityDialogTask): Boolean {
        ActivitySceneQueue.offer(element)
        if (element.priority == Priority.HIGH_REPLACE) {
            val current = ActivityConsumer.currentTask()
            if (current != null && current.priority != Priority.HIGH_REPLACE) {
                Log.d("PriorityQueue", "force cancel and replace")
                current.cancel("force cancel and replace")
            }
        }
        ActivityConsumer.notEmpty(element.scene)
        return true
    }

    fun empty(): Boolean {
        return ActivitySceneQueue.empty()
    }

    suspend fun lock(cancel: Boolean) {
        ActivityConsumer.lock(cancel)
    }

    suspend fun unlock() {
        ActivityConsumer.unlock()
    }

    suspend fun cleanActivityQueue() {
        ActivitySceneQueue.clean()
        currentActivityTask()?.cancel("clean")
    }

    fun allActivityTasks(): Map<Class<out IScene>, java.util.PriorityQueue<PriorityDialogTask>> {
        return ActivitySceneQueue.all()
    }

    fun currentActivityTask(): BaseTask? {
        return ActivityConsumer.currentTask()
    }
}