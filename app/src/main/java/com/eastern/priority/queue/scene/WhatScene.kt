package com.eastern.priority.queue.scene

import com.priority.queue.scene.AScene
import com.priority.queue.scene.IScene

/**
 * Time:2022/3/13 10:39 AM
 * Author: eastern
 * Description:
 */
class WhatScene : AScene() {
    override fun currentScene(): Class<out IScene> {
        return WhatScene::class.java
    }
}