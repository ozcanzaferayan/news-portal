package com.zaferayan.newsportal.ui.webview;

import android.app.Application;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.ui.webview.model.WebViewExtras;
import com.zaferayan.newsportal.util.ActionBarUtil;
import com.zaferayan.newsportal.util.BundleUtil;
import com.zaferayan.newsportal.util.ToastHelper;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_URL_DEFAULT = null;
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_TITLE_DEFAULT = "News";
    private Application app;
    private Resources resources;
    private WebViewExtras webViewExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        app = getApplication();
        resources = getResources();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webViewExtras = BundleUtil.getWebViewExtras(getIntent().getExtras());
        ActionBarUtil.setTitle(getSupportActionBar(), webViewExtras.getTitle());
        initWebView(webViewExtras.getUrl());
    }

    private void initWebView(String url) {
        if (webViewExtras.getUrl() == null) {
            ToastHelper.showError(app, resources.getString(R.string.webView_toast_url_not_found));
            finish();
            return;
        }
        WebView webView = findViewById(R.id.news_webview);
        webView.setWebViewClient(new WebViewClient());
        try {
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            ToastHelper.showError(app, e.getMessage());
        }
    }
}
