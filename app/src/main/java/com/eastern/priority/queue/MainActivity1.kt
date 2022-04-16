package com.eastern.priority.queue

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.IScene
import com.eastern.priority.R
import com.priority.queue.scene.HomeScene

class MainActivity1 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).text = "MainActivity1"
    }

    override fun currentScene(): Class<out IScene> {
        return HomeScene::class.java
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        postDelayed({
            startActivity(Intent(this@MainActivity1, MainActivity2::class.java))
            finish()
        }, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

}