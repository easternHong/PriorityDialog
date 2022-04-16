package com.eastern.priority.queue

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.priority.queue.scene.IScene
import com.eastern.priority.R
import com.eastern.priority.queue.scene.WhatScene

class MainActivity4 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).text = "MainActivity4"
    }

    override fun currentScene(): Class<out IScene> {
        return WhatScene::class.java
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        postDelayed({
            startActivity(Intent(this@MainActivity4, MainActivity5::class.java))
            finish()
        }, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }
}