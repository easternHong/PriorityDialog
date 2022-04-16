package com.priority.queue.queue

import com.priority.queue.scene.IScene
import com.priority.queue.task.BaseTask
import java.util.PriorityQueue
import java.util.concurrent.ConcurrentHashMap

/**
 * @author eastern
 * @date 2022/04/14
 **/
open class BaseQueue<T : BaseTask> : IBaseQueue<T> {
    protected val queueMap = ConcurrentHashMap<Class<out IScene>, PriorityQueue<T>>()

    override suspend fun offer(element: T) {
        val queue = queueMap[element.scene]
        if (queue == null) {
            queueMap[element.scene] = PriorityQueue()
        }
        queueMap[element.scene]?.offer(element)
    }

    override fun poll(scene: Class<out IScene>): T? {
        return queueMap[scene]?.poll()
    }

    fun empty(): Boolean {
        var count = 0
        queueMap.keys.forEach {
            count += queueMap[it]?.size ?: 0
        }
        return count == 0
    }

    override fun all(): Map<Class<out IScene>, PriorityQueue<T>> {
        return queueMap
    }

    override fun clean() {
        queueMap.clear()
    }
}

interface IBaseQueue<T : BaseTask> {
    suspend fun offer(element: T)

    fun poll(scene: Class<out IScene>): T?

    fun all(): Map<Class<out IScene>, PriorityQueue<T>>
    fun clean()
}