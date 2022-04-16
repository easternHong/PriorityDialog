package com.priority.queue.task

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.priority.queue.ERR_ACTIVITY_INVALID
import com.priority.queue.Priority
import com.priority.queue.Result
import com.priority.queue.checkActivityValid
import com.priority.queue.queue.BaseDialog
import com.priority.queue.queue.SceneDelegate
import com.priority.queue.scene.IScene
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author eastern
 * @date 2022/03/19
 **/
class PriorityDialogTask : BaseTask() {
    private val taskState = AtomicInteger()
    private var fragment: BaseDialog? = null
    var bundle: Bundle? = null
        private set
    var clazz: Class<out BaseDialog>? = null
        private set

    /**
     * if show failed ,re enqueue this task
     */
    var replay: Int = 0
        private set

    fun replay(replay: Int) = apply { this.replay = replay }
    fun bundle(bundle: Bundle) = apply { this.bundle = bundle }
    fun timeout(timeout: Long) = apply { this.timeout = timeout }
    fun scene(scene: Class<out IScene>) = apply { this.scene = scene }
    fun clazz(clazz: Class<out BaseDialog>) = apply { this.clazz = clazz }
    fun priority(priority: Priority) = apply { this.priority = priority }

    fun build() = PriorityDialogTask().also {
        it.clazz = clazz
        it.bundle = bundle
        it.priority = priority
        it.scene = scene
        it.timeout = timeout
        it.replay = replay
    }

    override suspend fun show(): Result<String> {
        try {
            if (timeout > 0L && System.currentTimeMillis() - time > timeout) {
                return Result.Failure(-1, "task is timeout")
            }
            if (taskState.getAndSet(1) == -1) {
                //task is cancel
                return Result.Failure(-1, "task is canceled")
            }
            val activity = SceneDelegate.INSTANCE.currentActivity()
            if (!checkActivityValid(SceneDelegate.INSTANCE.currentActivity()) || (activity !is FragmentActivity)) {
                reset()
                return Result.Failure(ERR_ACTIVITY_INVALID, "activity not valid$activity")
            }
            fragment = clazz!!.newInstance()
            fragment!!.arguments = bundle
            fragment!!.showNow(activity.supportFragmentManager, "$taskId")
        } catch (e: Throwable) {
            var reason = "something happen $e"
            if (taskState.get() == -1) {
                reason = "cancel"
            }
            fragment!!.setFinishResult(reason, e)
            return Result.Failure(-2, reason, e)
        }
        return fragment?.awaitDismiss() ?: Result.Failure(-2, "fragment is null")
    }

    override suspend fun cancel(reason: String) {
        super.cancel(reason)
        taskState.getAndSet(-1)
        fragment?.cancel(reason)
    }

    override fun reset() {
        taskState.getAndSet(0)
        replay--
        fragment = null
    }

    override fun toString(): String {
        return "Task(priority:$priority, bundle=$bundle, clazz=${clazz?.kotlin?.simpleName})"
    }
}