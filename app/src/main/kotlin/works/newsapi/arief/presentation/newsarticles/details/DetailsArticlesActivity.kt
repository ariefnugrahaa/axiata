package works.newsapi.arief.presentation.newsarticles.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_details_articles.*
import works.newsapi.arief.R
import works.newsapi.arief.common.extension.makeGone
import works.newsapi.arief.service.NavigationService

class DetailsArticlesActivity : AppCompatActivity() {

    private val extraUrl by lazy { intent.getStringExtra(NavigationService.EXTRA_WEBVIEW) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_articles)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webview_detail.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                shimmer_loading_detail.makeGone()
            }
        }
        webview_detail.apply {
            loadUrl(extraUrl)
            settings.javaScriptEnabled = true
        }
    }
}