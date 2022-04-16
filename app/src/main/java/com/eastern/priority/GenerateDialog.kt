package com.eastern.priority

import android.os.Bundle
import com.eastern.priority.queue.scene.TestScene
import com.eastern.priority.queue.scene.WhatScene
import com.priority.queue.Priority
import com.priority.queue.queue.ActivityDialogQueue
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.HomeScene
import com.priority.queue.task.PriorityDialogTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Random

/**
 * Time:2022/3/13 10:40 AM
 * Author: eastern
 * Description:
 */
object GenerateDialog {
    private val scope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    private val sceneList =
        listOf(GlobalScene::class.java, HomeScene::class.java, TestScene::class.java, WhatScene::class.java)

    init {
        init()
    }

    fun init() {
        scope.launch {
            for (i in 0..10) {
                val pri = if (i % 12 == 0) Priority.HIGH_REPLACE else {
                    Priority.values()[Random().nextInt(3) + 1]
                }
                val s = sceneList[(0..3).random()]
                val task = PriorityDialogTask()
                    .clazz(BlankFragment::class.java)
                    .scene(s)
                    .priority(
                        pri
                    )
                    .bundle(Bundle().also { b ->
                        b.putString("scene", "$s")
                        b.putString("content", if (pri == Priority.HIGH_REPLACE) "big xxx pro" else "$pri")
                    })
                    .build()
                ActivityDialogQueue.enqueue(task)
            }
        }
    }

    fun normal() {
        for (i in 0..1) {
            val pri = if (i > 50) Priority.HIGH_REPLACE else {
                Priority.values()[Random().nextInt(3) + 1]
            }
            val task = PriorityDialogTask()
                .clazz(BlankFragment::class.java)
                .scene(GlobalScene::class.java)
                .priority(
                    Priority.NORMAL
                )
                .bundle(Bundle().also { b ->
                    b.putString("scene", "xx")
                    b.putString("content", if (pri == Priority.HIGH_REPLACE) "big xxx pro" else "$pri")
                })
                .build()
            scope.launch {
                ActivityDialogQueue.enqueue(task)
            }
        }
    }
}