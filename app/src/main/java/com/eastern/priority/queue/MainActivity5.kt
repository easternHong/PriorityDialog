package com.eastern.priority.queue

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.eastern.priority.R
import com.eastern.priority.queue.scene.TestScene
import com.priority.queue.scene.IScene

class MainActivity5 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).text = "MainActivity5"
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        postDelayed({
            startActivity(Intent(this@MainActivity5, MainActivity6::class.java))
            finish()
        }, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    override fun currentScene(): Class<out IScene> {
        return TestScene::class.java
    }
}