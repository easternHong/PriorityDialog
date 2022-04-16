package com.priority.queue.queue

import android.util.Log
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.IScene
import com.priority.queue.task.BaseTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * @author eastern
 * @date 2022/04/14
 **/
class Consumer(val scene: IScene, private val iBaseQueue: BaseQueue<out BaseTask>, private val block: () -> Job?) :
    IConsumerLifecycle {
    private val isResumed = AtomicReference<Job?>(Job())
    private val isDestroyed = AtomicBoolean()
    private val notEmpty = AtomicReference<Job?>()
    private var loopJob: Job? = null
    private val scope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    private val currentTask = AtomicReference<BaseTask>()
    private fun loop() {
        loopJob?.cancel()
        loopJob = scope.launch {
            while (!isDestroyed.get()) {
                block()?.join()
                notEmpty.get()?.join()
                isResumed.get()?.join()
                if (isDestroyed.get()) {
                    break
                }
                val task = iBaseQueue.poll(scene.currentScene())
                Log.d("PriorityQueue", " start $scene task:$task")
                currentTask.getAndSet(task)
                if (task == null) {
                    notEmpty.getAndSet(Job())?.cancel()
                }
                val result = runBlocking {
                    withContext(Dispatchers.Main.immediate) {
                        task?.show()
                    }
                }
                Log.d("PriorityQueue", " end $task $result")
                currentTask.getAndSet(null)
                if (result is com.priority.queue.Result.Failure) {
                    Log.d("PriorityQueue", "err${result.throwable}")
                }
            }
        }
    }

    override fun create(scene: IScene?) {
        loop()
    }

    override fun resume(scene: IScene?) {
        isResumed.get()?.cancel()
    }

    override fun pause(scene: IScene?) {
        isResumed.getAndSet(Job())?.cancel()
    }

    override fun destroy(scene: IScene?) {
        isDestroyed.getAndSet(true)
        loopJob?.cancel()
        notEmpty.get()?.cancel()
        isResumed.get()?.cancel()
    }

    override fun clean() {
    }

    override fun notEmpty(scene: Class<out IScene>) {
        if (this.scene.currentScene() == scene || scene == GlobalScene::class.java) {
            notEmpty.getAndSet(null)?.cancel()
        }
    }

    override suspend fun cancel(reason: String) {
        currentTask.get()?.cancel(reason)
    }

    override fun currentTask(): BaseTask? {
        return currentTask.get()
    }
}