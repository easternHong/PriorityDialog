package com.eastern.priority

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.priority.queue.Priority
import com.priority.queue.queue.ActivityDialogQueue
import com.priority.queue.scene.GlobalScene
import com.priority.queue.task.PriorityDialogTask
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val scene = GlobalScene::class.java
        val task = PriorityDialogTask()
            .clazz(BlankFragment::class.java)
            .scene(scene)
            .priority(Priority.HIGH_REPLACE)
            .bundle(Bundle().also { b ->
                b.putString("scene", "xx")
                b.putString("content", "big bro")
            })
            .build()
        MainScope().launch {
            delay(1000)
            when (intent.getStringExtra("tag")) {
                "lock" -> {
                    ActivityDialogQueue.lock(true)
                }
                "unlock" -> {
                    ActivityDialogQueue.unlock()
                }
                "clean" -> {
                    ActivityDialogQueue.cleanActivityQueue()
                }
                "enqueue" -> {
                    ActivityDialogQueue.enqueue(task)
                }
            }
        }
    }
}