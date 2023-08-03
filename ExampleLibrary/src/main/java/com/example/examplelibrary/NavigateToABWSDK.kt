package com.example.examplelibrary

import android.content.Context
import android.content.Intent

object NavigateToABWSDK {

    fun navigateToSDK(context : Context){
        val intent = Intent(context,TestWebview::class.java)
        context.startActivity(intent)
    }

}