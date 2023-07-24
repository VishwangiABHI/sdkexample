package com.example.examplelibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.examplelibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        onDHACardClick()
    }

    fun onDHACardClick(){
        binding.tvActiveMindStart.setOnClickListener {
            Log.e("in","in webview")

            val intent = Intent(this@MainActivity,TestWebview::class.java)
            startActivity(intent)
        }

    }
}