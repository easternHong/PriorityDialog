package com.priority.queue.queue

import com.priority.queue.scene.IScene
import com.priority.queue.task.BaseTask
import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicReference

/**
 * @author eastern
 * @date 2022/04/15
 **/
internal object ActivityConsumer : IConsumerLifecycle, ILock {
    private val sceneMap = HashMap<IScene, IConsumerLifecycle>()
    private val currentScene = AtomicReference<IConsumerLifecycle>(null)
    private val queue by lazy { ActivitySceneQueue }
    private val lock = AtomicReference<Job?>()
    override fun create(scene: IScene?) {
        sceneMap[scene!!] = Consumer(scene, queue) {
            lock.get()
        }.also {
            it.create(scene)
        }
    }

    override fun resume(scene: IScene?) {
        sceneMap.keys.forEach {
            if (it == scene) {
                currentScene.getAndSet(sceneMap[it])
                sceneMap[it]?.resume(scene)
            } else {
                sceneMap[it]?.pause(scene)
            }
        }
    }

    override fun pause(scene: IScene?) {
        sceneMap[scene]?.pause(scene)
    }

    override fun destroy(scene: IScene?) {
        sceneMap.remove(scene)?.destroy(scene)
    }

    override fun clean() {
        currentScene.get()?.destroy(null)
    }

    override fun notEmpty(scene: Class<out IScene>) {
        currentScene.get()?.notEmpty(scene)
    }

    override suspend fun cancel(reason: String) {
        currentScene.get()?.cancel(reason)
    }

    override fun currentTask(): BaseTask? {
        return currentScene.get()?.currentTask()
    }

    override suspend fun lock(cancel: Boolean) {
        if (lock.get() == null) {
            lock.getAndSet(Job())?.cancel()
        }
        if (cancel) {
            cancel("cancel by lock")
        }
    }

    override suspend fun unlock() {
        lock.getAndSet(null)?.cancel()
    }
}