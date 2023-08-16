package com.example.examplelibrary

import android.content.Context
import android.content.Intent

object NavigateToABWSDK {

    fun navigateToSDK(context : Context,customerId : String, firstName : String, lastName : String, clientCode : String){
        val intent = Intent(context,TestWebview::class.java)
        intent.putExtra("customerId",customerId);
        intent.putExtra("firstName",firstName);
        intent.putExtra("lastName",lastName);
        intent.putExtra("clientCode",clientCode);
        context.startActivity(intent)
    }

}