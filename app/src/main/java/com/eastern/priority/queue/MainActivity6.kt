package com.eastern.priority.queue

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import com.eastern.priority.MainActivity
import com.eastern.priority.R
import com.eastern.priority.queue.scene.WhatScene
import com.priority.queue.scene.IScene
import kotlinx.coroutines.Delay

class MainActivity6 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).text = "MainActivity6"
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        postDelayed({
            finish()
            startActivity(Intent(this@MainActivity6, MainActivity::class.java))
        }, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    override fun currentScene(): Class<out IScene> {
        return WhatScene::class.java
    }
}