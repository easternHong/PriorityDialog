package com.priority.queue

/**
 * Time:2022/3/12 11:00 AM
 * Author: eastern
 * Description:
 */

sealed class Result<T> {
    /**
     * 调用执行成功，返回结果[value]
     */
    data class Success<T>(val value: T) : Result<T>()

    /**
     * 调用执行失败
     * 错误码[errorCode]，不填默认-1
     * 错误信息[errorMessage]，不填默认为空字符串
     * 异常[throwable]，不填默认为空
     */
    data class Failure<T>(
        val errorCode: Int = -1, val errorMessage: String = "", val throwable: Throwable? = null
    ) : Result<T>()

    /**
     * 不关心成功与否，只要取值
     */
    fun getOrNull() = if (this is Success) value else null
}
