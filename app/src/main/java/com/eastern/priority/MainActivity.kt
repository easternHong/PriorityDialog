package com.eastern.priority

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.eastern.priority.queue.EmptyActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val scope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    private val _userInfoFlow by lazy {
        MutableStateFlow("xxxx")
    }
    private val userInfoFlow: StateFlow<String?> = _userInfoFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, EmptyActivity::class.java))
        finish()
//        val strong = NotifyFragment().also {
//            it.iContentFetcher = VideoMatchNotifyFragmentFetcher()
//        }
//        supportFragmentManager.beginTransaction()
//            .add(android.R.id.content, strong)
//            .commitNowAllowingStateLoss()
        scope.launch {
            _userInfoFlow.drop(1).collect {
                Log.d("MainActivity", "this. is first:$it")
            }
        }
        scope.launch {
            _userInfoFlow.drop(1).collect {
                Log.d("MainActivity", "this. is second:$it")
            }
        }
        scope.launch {
//            for (i in 0..100) {
//                delay(40)
//                _userInfoFlow.emit(i.toString())
//            }
        }
    }
}