package com.example.examplelibrary

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.examplelibrary.databinding.ActivityMainBinding
import com.example.examplelibrary.databinding.ActivityTestWebviewBinding

class TestWebview : AppCompatActivity(), AdvancedWebView.Listener {
    lateinit var binding : ActivityTestWebviewBinding
    lateinit var pdfDownloader: PdfDownloader
    var permissionRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@TestWebview,R.layout.activity_test_webview)
        initView()
        requestRuntimePermission()
        binding.textHeader.text = "DHA"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        binding.webview.setListener(this, this)
        binding.webview.setGeolocationEnabled(true)
        binding.webview.setMixedContentAllowed(true)
        binding.webview.setCookiesEnabled(true)
        binding.webview.setThirdPartyCookiesEnabled(true)
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webChromeClient = MyChrome()
        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl("https://mtpre.adityabirlahealth.com/execute/journey/0848dea1-338a-48d1-9aac-0e5a901c32eb?member_id=&wellness_id=5&user_id=5&source=multiple&dob=1992-07-15&mobile_no=8108569103&gender=Male&first_name=Rohan&email=rohanvishwakarma182@gmail.com&last_name=vishwakarma")
        binding.webview.addJavascriptInterface(
            WebViewJavaScriptInterface(this@TestWebview),
            "Android"
        )
        pdfDownloader = PdfDownloader()

    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
    }

    override fun onPageFinished(url: String?) {
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
    }

    internal class WebViewJavaScriptInterface(private val context: Context) {
        @JavascriptInterface
        fun postMessage(message: String) {
            Log.v(TAG, "message ----$message")
        }

        companion object {
            private const val TAG = "WebView"
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequestCode) {
            try {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(applicationContext,"Please give camera permissions",Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()

            }
        }
    }


    fun requestRuntimePermission(){
        if (applicationContext?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA
                ), permissionRequestCode
            )
        } else {
            binding.webview.loadUrl("https://mtpre.adityabirlahealth.com/execute/journey/0848dea1-338a-48d1-9aac-0e5a901c32eb?member_id=&wellness_id=5&user_id=5&source=multiple&dob=1992-07-15&mobile_no=8108569103&gender=Male&first_name=Rohan&email=rohanvishwakarma182@gmail.com&last_name=vishwakarma")
        }
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
        Log.d("mytag", "onDownloadRequested() url:$url, mimeType:$mimeType");

        try {
            url?.let {
                val uri = Uri.parse(url)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }catch (e:Exception){
            // Toast.makeText(this@GetVisitWebview,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onExternalPageRequest(url: String?) {
    }

    inner class MyChrome internal constructor() : WebChromeClient() {
        private var mCustomView: View? = null
        private var mCustomViewCallback: CustomViewCallback? = null
        private var mOriginalOrientation = 0
        private var mOriginalSystemUiVisibility = 0

        override fun onHideCustomView() {
            (window.decorView as FrameLayout).removeView(mCustomView)
            mCustomView = null
            window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
            requestedOrientation = mOriginalOrientation
            mCustomViewCallback!!.onCustomViewHidden()
            mCustomViewCallback = null
        }

        override fun onShowCustomView(
            paramView: View, paramCustomViewCallback: CustomViewCallback
        ) {
            if (mCustomView != null) {
                onHideCustomView()
                return
            }
            mCustomView = paramView
            mOriginalSystemUiVisibility = window.decorView.getSystemUiVisibility()
            mOriginalOrientation = requestedOrientation
            mCustomViewCallback = paramCustomViewCallback
            (window.decorView as FrameLayout).addView(
                mCustomView, FrameLayout.LayoutParams(-1, -1)
            )
            window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    private fun initView() {
        binding.rlHeader.setOnClickListener {
            val alertDialog =
                AlertDialog.Builder(this@TestWebview, R.style.AlertDialogTheme)
                    .setCancelable(false)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes"
                    ) { dialog: DialogInterface?, which: Int -> finish() }
                    .setNegativeButton("No"
                    ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                    .create()
            alertDialog.show()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webview.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.webview.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

}