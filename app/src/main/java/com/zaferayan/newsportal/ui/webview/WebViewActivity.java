package com.zaferayan.newsportal.ui.webview;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.zaferayan.newsportal.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_URL_DEFAULT = null;
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_TITLE_DEFAULT = "News";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            showErrorMessage(getResources().getString(R.string.webView_toast_url_not_found));
            return;
        }
        String url = getIntent().getExtras().getString(EXTRA_URL, EXTRA_URL_DEFAULT);
        String title = getIntent().getExtras().getString(EXTRA_TITLE, EXTRA_TITLE_DEFAULT);
        ActionBar actionBar = getSupportActionBar();
        setActionBar(title, actionBar);
        if (url == null) {
            showErrorMessage(getResources().getString(R.string.webView_toast_url_not_found));
            return;
        }
        WebView webView = findViewById(R.id.news_webview);
        webView.setWebViewClient(new WebViewClient());
        try {
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        }


    }

    private void setActionBar(String title, ActionBar actionBar) {
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }
}
