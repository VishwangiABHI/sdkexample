package com.example.examplelibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.examplelibrary.databinding.ActivityMainBinding

class SDKExampleActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showCard()
    }

    fun showCard(){
        binding = DataBindingUtil.setContentView(this@SDKExampleActivity,R.layout.activity_main)
        binding.tvActiveMindStart.setOnClickListener {
            Log.e("in","in webview")
            val intent = Intent(this@SDKExampleActivity,TestWebview::class.java)
            startActivity(intent)
        }
    }
}