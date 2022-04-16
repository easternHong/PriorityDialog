package com.priority.queue.task

import com.priority.queue.ActionFetcher
import com.priority.queue.Priority
import com.priority.queue.Result
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.IScene

/**
 *@author eastern
 *@date 2022/03/19
 **/
abstract class BaseTask(
    var priority: Priority = Priority.NORMAL,
    var scene: Class<out IScene> = GlobalScene::class.java,
    var timeout: Long = 0,
    val taskId: Long = ActionFetcher.fetchTaskId()
) : Comparable<BaseTask> {
    override fun compareTo(other: BaseTask): Int {
        if (other.priority == priority) {
            //同级别的，按照入列的FIFO
            return other.time.compareTo(time)
        }
        /**
         * 升序
         */
        return priority.ordinal.compareTo(other.priority.ordinal)
    }

    protected val time = System.currentTimeMillis()

    open suspend fun show(): Result<String> {
        return Result.Success("")
    }

    open suspend fun cancel(reason: String) {}

    open fun reset() {}
}