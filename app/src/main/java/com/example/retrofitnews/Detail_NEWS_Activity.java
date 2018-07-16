package com.example.retrofitnews;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class Detail_NEWS_Activity extends AppCompatActivity {
    android.app.AlertDialog dialog;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news_activity);

        dialog = new SpotsDialog.Builder().setContext(this).build();
        dialog.show();
        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        if(getIntent()!=null){
            if(!getIntent().getStringExtra("url").isEmpty()){
                webView.loadUrl(getIntent().getStringExtra("url"));
            }
        }

    }
}
