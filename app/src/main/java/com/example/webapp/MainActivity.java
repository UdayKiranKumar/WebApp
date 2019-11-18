package com.example.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    WebView mwebview;
    ProgressBar progressBar;   //it will show the progress of page loading
    String sb="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mwebview = findViewById(R.id.webview);
        progressBar = findViewById(R.id.pb);

        if(savedInstanceState == null)
            //to load a web url into the WebView but you can not interact with the page
            mwebview.loadUrl("https://www.google.com/");

/*JavaScript is by default turned off in WebView widgets. Hence
 web pages containing javascript references won’t work properly.*/
        mwebview.getSettings().setJavaScriptEnabled(true);
        mwebview.setWebViewClient(new MyWebViewClient());  //to fetch WebViewClient class
        mwebview.setWebChromeClient(new MyWebCromeClient());
    }
    /*If you click on any link inside the webpage of the WebView
    , that page will not be loaded inside your WebView of app
    instead it will go to that particular link outside the app.
    to fetch it in app  you need to get the features of
    WebViewClient class
     */
    class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getSupportActionBar().setTitle(view.getTitle());
        }
    }
    /* WebChromeClient is used to handle a JavaScript events in
    Android App which are  produced by WebView.WebChromeClient is
    an event interface for reacting to events that should change
    elements of chrome around the browser*/
    class MyWebCromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //super.onReceivedTitle(view, title);
            sb= title;
            getSupportActionBar().setTitle(title);

        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }
    }

    /*to interact with the web page i.e. to go back and forward if there is
any history found through the options of menu.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int k = item.getItemId();

        switch (k)
        {
            case R.id.back:
                if(mwebview.canGoBack())
                    mwebview.goBack();
                    break;
            case R.id.forward:
                if(mwebview.canGoForward())
                    mwebview.goForward();
                    break;
            case R.id.reload:
                mwebview.reload();
                break;
        }
        return true;
    }

    /* If we click the back button in the app developed so far
    we see that the application returns to the home screen even
     though we’ve navigated through a few pages within the
     WebView itself*/
    @Override
    public void onBackPressed() {
        //getSupportActionBar().setTitle(sb);
        if(mwebview.canGoBack())
        {
            mwebview.goBack();
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mwebview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mwebview.restoreState(savedInstanceState);
    }
}
