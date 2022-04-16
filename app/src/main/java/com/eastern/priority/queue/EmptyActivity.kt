package com.eastern.priority.queue

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.eastern.priority.R

class EmptyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_test).text = "EmptyActivity"
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
        postDelayed({
            startActivity(Intent(this@EmptyActivity, MainActivity1::class.java))
            finish()
        }, 5000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }
}