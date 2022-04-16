package com.priority.queue.queue

import com.priority.queue.scene.IScene
import com.priority.queue.task.BaseTask

/**
 * @author eastern
 * @date 2022/04/14
 **/

interface INewItem {
    fun notEmpty(scene: Class<out IScene>)
}

interface ILock {
    suspend fun lock(cancel: Boolean)
    suspend fun unlock()
}

interface IConsumerLifecycle : INewItem {

    fun create(scene: IScene?)

    fun resume(scene: IScene?)

    fun pause(scene: IScene?)

    fun destroy(scene: IScene?)

    fun currentTask(): BaseTask?

    fun clean()

    suspend fun cancel(reason: String)
}