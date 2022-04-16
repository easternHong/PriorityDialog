package com.eastern.priority.queue

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.priority.queue.scene.GlobalScene
import com.priority.queue.scene.IScene
import com.eastern.priority.R

class MainActivity2 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).text = "MainActivity2"
    }

    override fun currentScene(): Class<out IScene> {
        return GlobalScene::class.java
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        postDelayed({
            startActivity(Intent(this@MainActivity2, MainActivity3::class.java))
            finish()
        }, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }
}