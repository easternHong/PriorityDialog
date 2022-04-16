package com.priority.queue.scene

/**
 * Time:2022/3/15 2:16 PM
 * Author: eastern
 * Description:
 */
class HomeScene : IScene {
    override fun currentScene(): Class<out IScene> {
        return HomeScene::class.java
    }
}