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
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.examplelibrary.databinding.ActivityTestWebviewBinding
import java.util.*

class TestWebview : AppCompatActivity(), AdvancedWebView.Listener {
    lateinit var binding : ActivityTestWebviewBinding
    lateinit var pdfDownloader: PdfDownloader
    var permissionRequestCode = 1
    private val CAMERA_MIC_PERMISSION_REQUEST_CODE = 700
    var webURL = "https://mtpre.adityabirlahealth.com/execute/journey/0848dea1-338a-48d1-9aac-0e5a901c32eb?member_id=6&wellness_id=6&user_id=6&source=multiple&source=android&dob=1992-07-15&mobile_no=8108569103&gender=Male&first_name=Rohan&email=rohanvishwakarma182@gmail.com&last_name=vishwakarma"
    private var cameraPermission: PermissionRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@TestWebview,R.layout.activity_test_webview)
        initView()
        val random = Random()
        random.nextInt()
        //val random = 100
        binding.textHeader.text = "DHA"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        if (!keepCache) {
//            Log.i("zzz webView", "!keepCache")
//            binding.webview.clearCache(true)
//            binding.webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE)
//            WebStorage.getInstance().deleteAllData()
//        }

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.domStorageEnabled = true
        binding.webview.settings.setSupportMultipleWindows(true)
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webview.settings.allowFileAccessFromFileURLs = true
        binding.webview.settings.allowUniversalAccessFromFileURLs = true
        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        binding.webview.setListener(this, this)
        binding.webview.setGeolocationEnabled(true)
        binding.webview.setMixedContentAllowed(true)
        binding.webview.setCookiesEnabled(true)
        binding.webview.setThirdPartyCookiesEnabled(true)
        binding.webview.webChromeClient = MyChrome()
        binding.webview.webViewClient = WebViewClient()
        val url = "https://mtpre.adityabirlahealth.com/execute/journey/650115dd-b2ec-4cc6-94c4-dd3367a61590?&token=&customer_id=2&firstname=rohan&lastname=vish&source=abcd&clientcode=abcd"
        //binding.webview.loadUrl("https://mtpre.adityabirlahealth.com/execute/journey/0848dea1-338a-48d1-9aac-0e5a901c32eb?member_id=$random&wellness_id=$random&user_id=$random&source=multiple&source=android&dob=1992-07-15&mobile_no=8108569103&gender=Male&first_name=Rohan&email=rohanvishwakarma182@gmail.com&last_name=vishwakarma")
        binding.webview.loadUrl(url)
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

        override fun onPermissionRequest(request: PermissionRequest) {
            Log.i("zzz webView", "onPermission")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val resultCamera = ActivityCompat.checkSelfPermission(
                    this@TestWebview,
                    Manifest.permission.CAMERA
                )
                val resultMic = ContextCompat.checkSelfPermission(
                    this@TestWebview,
                    Manifest.permission.RECORD_AUDIO
                )
                if (resultCamera == PackageManager.PERMISSION_GRANTED &&
                    resultMic == PackageManager.PERMISSION_GRANTED
                ) {
                    request.grant(request.resources)
                } else {
                    showCameraMicPermission()
                    cameraPermission = request
                }
            }
        }

        override fun onPermissionRequestCanceled(request: PermissionRequest?) {
            super.onPermissionRequestCanceled(request)
            Log.i("zzz webView", "onPermissionRequestCanceled")
        }
    }

        override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                CAMERA_MIC_PERMISSION_REQUEST_CODE -> {
                    //                 If request is cancelled, the result arrays are empty.
                    if (grantResults.isNotEmpty()) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    Log.i("zzz webView","onAccessCamera");
                            cameraPermission?.grant(cameraPermission!!.getResources())
                        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
//                    Log.i("zzz webView","onDenied");
                            showCameraMicPermissionDialog()
                        } else {
                            Toast.makeText(
                                this@TestWebview,
                                "Unable to Access Camera",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@TestWebview,
                            "Unable to Access Camera",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return
                }
            }
    }

    private fun showCameraMicPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ABW")
        builder.setPositiveButton(android.R.string.ok, null)
        builder.setMessage(
            "Please enable Camera & Microphone permission to proceed with DHA."
        )
        builder.setOnDismissListener {
            //                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@TestWebview,
                    Manifest.permission.CAMERA
                )
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@TestWebview,
                        Manifest.permission.RECORD_AUDIO
                    )
                ) {
                    showCameraMicPermission()
                }
            }
        }
        builder.show()
    }

    private fun showCameraMicPermission() {
        ActivityCompat.requestPermissions(
            this@TestWebview, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ), CAMERA_MIC_PERMISSION_REQUEST_CODE
        )
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