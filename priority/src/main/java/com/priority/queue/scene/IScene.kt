package com.priority.queue.scene

/**
 * Time:2022/3/10 5:29 PM
 * Author: eastern
 * Description:
 */
interface IScene {
    fun currentScene(): Class<out IScene>
}