package abhijit.osdm_wop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewsViewActivity extends AppCompatActivity {
    private WebView mWebview;
    private int ViewSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_news_view);

        Intent intent = getIntent();
        String newsurl = intent.getStringExtra("newsurl");
        String pdfdata = intent.getStringExtra("newsdata");

        System.out.println("News URL is >> "+newsurl);
       // newsurl="www.google.com";
        //Setup webview
        mWebview  = new WebView(this);
       // mWebview = (WebView)findViewById(R.id.newsView);
        // emailshare=(Button) findViewById(R.id.share_email);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebview .loadUrl(newsurl);
        setContentView(mWebview );
    }
}
