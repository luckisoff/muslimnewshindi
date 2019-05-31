package com.example.safi.muslimissues.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.safi.muslimissues.R;

public class VisitSourceActivity extends Activity {

    private Toolbar mToolbar;
    private TextView title_on_appbar,subtitle_on_appbar;
    private String strTitle,strUrl;
    private WebView webView;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_source);
        intent=getIntent();

        thread();


    }

    private void thread(){
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               getValues();
               setValues();
               showWebsite();


               mToolbar = (Toolbar) findViewById(R.id.toolbar);
               mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

               mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       finish();
                   }
               });
           }
       });
    }

    private void getValues(){
        strTitle=intent.getStringExtra("title");
        strUrl=intent.getStringExtra("url");
        title_on_appbar=findViewById(R.id.title_on_appbar);
        subtitle_on_appbar=findViewById(R.id.subtitle_on_appbar);
        webView=findViewById(R.id.webView);
    }

    private void setValues(){
        title_on_appbar.setText(strTitle);
        subtitle_on_appbar.setText(strUrl);
    }

    private void showWebsite(){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(strUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

}
