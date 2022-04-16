package com.priority.queue.queue

import android.util.Log
import com.priority.queue.Priority
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.IScene
import com.priority.queue.task.PriorityDialogTask

/**
 * @author eastern
 * @date 2022/04/14
 **/
internal object ActivitySceneQueue : BaseQueue<PriorityDialogTask>() {

    override suspend fun offer(element: PriorityDialogTask) {
        super.offer(element)
    }

    override fun poll(scene: Class<out IScene>): PriorityDialogTask? {
        //1. global Highest priority
        val globalQueue = queueMap[GlobalScene::class.java]
        val high = globalQueue?.peek()?.priority == Priority.HIGH_REPLACE
        if (high) {
            return globalQueue?.poll()
        }
        Log.d("PriorityQueue", "scene:$scene ${queueMap[scene]?.size} global:${globalQueue?.size}")
        return queueMap[scene]?.poll() ?: globalQueue?.poll()
    }
}