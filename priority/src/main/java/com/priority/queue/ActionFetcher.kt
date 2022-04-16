package com.priority.queue

import java.util.concurrent.atomic.AtomicLong

/**
 * Time:2022/3/12 11:03 AM
 * Author: eastern
 * Description:
 */

object ActionFetcher {
    private val list = LinkedHashMap<Long, Runnable>()
    private val index = AtomicLong(1000)
    private val id = AtomicLong(1000)
    fun fetchAction(runnable: Runnable): Long {
        val i = index.incrementAndGet()
        list[i] = runnable
        return i
    }

    fun fetchAction(id: Long): Runnable? {
        return list.remove(id)
    }

    fun fetchTaskId(): Long {
        return index.incrementAndGet()
    }
}