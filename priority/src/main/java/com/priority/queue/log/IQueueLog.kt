package com.priority.queue.log

/**
 * Time:2022/3/21 9:49 AM
 * Author: eastern
 * Description:
 */
interface IQueueLog {
    fun i(tag: String, message: String?)
    fun e(tag: String, message: String?)
    fun e(tag: String, message: String?, t: Throwable?)
}