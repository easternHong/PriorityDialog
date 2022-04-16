package com.priority.queue.queue

import androidx.fragment.app.DialogFragment
import com.priority.queue.Result
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

open class BaseDialog : DialogFragment() {
    private val job: CompletableJob = Job()
    private var reason = Pair<String?, Throwable?>(null, null)
    private val dialogCancel = AtomicInteger(0)
    private val dismissInvoke = AtomicBoolean(false)

    override fun onResume() {
        super.onResume()
        if (dialogCancel.get() == 1) {
            selfDismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setFinishResult(reason.first ?: "onDestroy")
    }

    suspend fun awaitDismiss(): Result<String> {
        job.join()
        if (reason.second != null) {
            return Result.Failure(-1, reason.first ?: "xxx", reason.second)
        }
        return Result.Success(reason.first ?: "normal")
    }

    open fun setFinishResult(reason: String, e: Throwable? = null) {
        this.reason = Pair(reason, e)
        job.complete()
        job.cancel()
    }

    open fun cancel(reason: String) {
        setFinishResult(reason)
        dialogCancel.getAndSet(1)
        selfDismiss()
    }

    private fun selfDismiss() {
        if (!isAdded) {
            return
        }
        if (!dismissInvoke.getAndSet(true)) {
            dismissAllowingStateLoss()
        }
    }
}