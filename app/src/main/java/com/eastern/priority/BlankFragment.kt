package com.eastern.priority

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import com.priority.queue.queue.BaseDialog

/**
 */
class BlankFragment : BaseDialog() {
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "${arguments?.getInt("priority")} ${arguments?.getString("scene") ?: "none"}" + "\n " +
                "${arguments?.getString("content")}"
        view.findViewById<TextView>(R.id.tv_test).text = text
        handler.postDelayed({
            Log.d("BaseQueue", "timeout dismiss")
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this).commitNowAllowingStateLoss()
        }, if (arguments?.getInt("priority") == 0) 2000 else 1500)
        view.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this).commitNowAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}