package com.priority.queue

/**
 * Time:2022/3/12 11:04 AM
 * Author: eastern
 * Description:
 */
enum class Priority {
    HIGH_REPLACE,
    HIGH,   //会放到队列头
    MIDDLE, //普通队列，FILO，优先级稍高一点
    NORMAL  //
}