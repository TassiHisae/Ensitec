package com.example.ensitec.BackgroundTask;

import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ensitec.R;

public class Background_Task_Noticias extends AsyncTask<WebView,WebView,WebView> {

    @Override
    protected WebView doInBackground(WebView...webView_noticia ) {

        try {

//            WebSettings webSettings = webView_noticia[0].getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            webView_noticia[0].setWebViewClient(new WebViewClient());
//            webView_noticia[0].loadUrl("https://www.cps.sp.gov.br/noticias/");

        }catch(Exception e){

            System.out.println(e);
        }
        return null;
    }
}
