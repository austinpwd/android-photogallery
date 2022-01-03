package ca.adoan.photo_gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {

    private val webView: WebView by lazy {findViewById(R.id.webview_activity)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val openBundle = this.intent.extras
        val photographerName = openBundle!!.getString("photographerName")
        val webUrl = openBundle.getString("photographerDetailsUrl")

        this.title = "Portfolio for $photographerName"

        webView.webViewClient = WebViewClient()
        webView.apply {
            loadUrl(webUrl!!)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val menuItemBack: MenuItem = menu!!.findItem(R.id.menu_item_back)
        val menuItemRefresh: MenuItem = menu.findItem(R.id.menu_item_refresh)
        val menuItemSearch: MenuItem = menu.findItem(R.id.menu_item_search)

        menuItemBack.isVisible = true
        menuItemRefresh.isVisible = false
        menuItemSearch.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (webView.canGoBack()){
            webView.goBack()
        }
        else{
            this.finish()
        }
        return true
    }

}