package com.zisoso.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MyAndroidActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	Button button2;
	WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        webView = (WebView)findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);  
		webView.loadUrl("http://www.baidu.com");  
		webView.setWebViewClient(new MyWebViewClient()); 
        button.setOnClickListener(this);

    }

	public void onClick(View v) {
		
	}
	//web ”ÕºøÕªß∂À  
    public class MyWebViewClient extends WebViewClient  
    {  
        public boolean shouldOverviewUrlLoading(WebView view,String url)  
        {  
            view.loadUrl(url);  
            return true;  
        }  
    }  
}