package net.wicp.yunjigroup.oa.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.net.Request;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
import net.wicp.yunjigroup.oa.utils.Utils.FileDownListener;
import net.wicp.yunjigroup.oa.view.MyWebChromeClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.DownloadListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class ScheduleDetailActivity extends BaseActivity implements OnClickListener, DownloadListener{
    private WebView webView = null;
    private android.webkit.ValueCallback<android.net.Uri> mUploadmsg = null;
    private ImageView mBackImg = null;
    private TextView mTitleTv = null;
    private boolean isDownUrl = false;
    private Dialog dialog;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);
        initViews();
    }
    
    @Override
    public void initViews() {
        String title = getIntent().getStringExtra("title");
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(title);
        webView = (WebView) findViewById(R.id.schedule_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading( WebView view, String url ) {
//                view.loadUrl(url);
                return false;
            }
            
            @Override
            public void onPageStarted( WebView view, String url, Bitmap favicon ) {
                super.onPageStarted(view, url, favicon);
                if(!isDownUrl)ProgressBarUtils.show(mContext, "");
            }
            
            @Override
            public void onPageFinished( WebView view, String url ) {
                super.onPageFinished(view, url);
                if(!isDownUrl) ProgressBarUtils.hide();
            }
            
            @Override
            public WebResourceResponse shouldInterceptRequest( WebView view, String url ) {
                System.out.println("url--c-->>"+url);
                return super.shouldInterceptRequest(view, url);
            }
            
        });
        webView.setDownloadListener(this);
        webView.setWebChromeClient(myWebChromeClient);
        webView.getSettings().setLoadWithOverviewMode(true);
        String url = Request.HOST + getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent intent ) {
        if (requestCode == 0) {
            if (null == mUploadmsg)
             return;
            Uri result = intent == null || resultCode != RESULT_OK ? null
              : intent.getData();
            mUploadmsg.onReceiveValue(result);
            mUploadmsg = null;
           }
    }
    
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
        
    }
    
    private MyWebChromeClient myWebChromeClient = new MyWebChromeClient(new WebChromeClient()) {
        
        public void openFileChooser(android.webkit.ValueCallback<android.net.Uri> uploadFile, String acceptType, String capture) {
            openFileChooser(uploadFile);
        };
        
        public void openFileChooser(android.webkit.ValueCallback<android.net.Uri> uploadMsg) {
            mUploadmsg = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            ((Activity)mContext).startActivityForResult(Intent.createChooser(intent, "文件选择"), 0);
        };
        
        public boolean onJsAlert(final WebView view, String url, String message, final android.webkit.JsResult result) {
            view.goBack();
            AlertDialog.Builder builder = null;
            builder = new AlertDialog.Builder(mContext)
                    .setTitle("网页提示")
                     .setMessage(message)
                     .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface arg0, int arg1 ) {
                            result.cancel();
                        }
                    });
            dialog = builder.create();
            dialog.show();
            return true;
        };
        
        public boolean onJsConfirm(WebView view, String url, String message, android.webkit.JsResult result) {
            return false;
        };
        
    };
    @Override
    public void onClick( View v ) {
        if(v.getId() == R.id.title_back){
            finish();
        }
    }

    @Override
    public void onDownloadStart( String url, String userAgent, String contentDisposition,
            final String mimetype, long contentLength ) {
        isDownUrl = true;
        final String fileName = Uri.decode(Utils.getFileNameFromUrl(url));
        final String filedir = Contants.CACHE_PATH + "/schedule";
        Utils.downFile(url, filedir, fileName, new FileDownListener() {
            @Override
            public void onStart() {
            }
            
            @Override
            public void onFaile() {
                ProgressBarUtils.hide();
            }
            
            @Override
            public void onComplete() {
                ProgressBarUtils.hide();
                isDownUrl = false;
                Uri uri = Uri.fromFile(new File(filedir,fileName)); 
                Utils.openAccessory(mContext, uri, mimetype);
            }
        });
    }
    
}
