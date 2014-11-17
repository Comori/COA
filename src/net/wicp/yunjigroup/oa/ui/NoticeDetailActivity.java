package net.wicp.yunjigroup.oa.ui;

import java.util.ArrayList;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.NoticeDetail;
import net.wicp.yunjigroup.oa.models.NoticeSimple;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoticeDetailActivity extends BaseActivity implements OnClickListener{
    public static final String TAG = "通知公告详情";
    
    private TextView mTitleTv = null;
    private ImageView mBackImg = null;
    private TextView noticeTitleTv,departmentTv,timeTv,otherTv;
    private NoticeSimple noticeSimple = null;
    private NoticeDetail noticeDetail = null;
    private WebView contentView = null;
    private RelativeLayout noticeTitleLayout = null;
    
    class NoticeDetailTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarUtils.show(mContext,null);
        }
        
        @Override
        protected Void doInBackground( Void... params ) {
            noticeDetail = NetEngine.getInstance(mContext).getNoticeDetail(noticeSimple.getId(), noticeSimple.getCard());
            return null;
        }
        
        @Override
        protected void onPostExecute( Void result ) {
            super.onPostExecute(result);
            ProgressBarUtils.hide();
            if(noticeDetail != null){
                String content = noticeDetail.getContent();
                contentView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                if(noticeDetail.hasAccessory()){
                    otherTv.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_detail);
        initViews();
        initDatas();
        
        if(noticeSimple != null) {
            new NoticeDetailTask().execute();
        }
    }
    
    @Override
    public void initViews() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mTitleTv.setText(TAG);
        mBackImg.setOnClickListener(this);
        noticeTitleLayout = (RelativeLayout) findViewById(R.id.notice_detail_titleLayout);
        noticeTitleLayout.setOnClickListener(this);
        noticeTitleTv = (TextView) findViewById(R.id.notice_detail_title);
        departmentTv = (TextView) findViewById(R.id.notice_detail_department);
        timeTv = (TextView) findViewById(R.id.notice_detail_time);
        otherTv = (TextView) findViewById(R.id.notice_detail_other);
        contentView = (WebView) findViewById(R.id.notice_detail_content);
        contentView.getSettings().setJavaScriptEnabled(true);
        contentView.getSettings().setSupportZoom(true);
    }
    
    private void initDatas() {
        Intent intent = getIntent();
        noticeSimple = (NoticeSimple) intent.getSerializableExtra("noticeSimple");
        if(noticeSimple != null){
            noticeTitleTv.setText(noticeSimple.getTitle());
            departmentTv.setText(noticeSimple.getDepartment());
            timeTv.setText(noticeSimple.getSendTime());
        }
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.notice_detail_titleLayout:
            if(otherTv.getVisibility() == View.VISIBLE){
                Intent intent = new Intent(mContext, AccessoryListActivity.class);
                intent.putStringArrayListExtra("paths", (ArrayList<String>) noticeDetail.getAccessoryPaths());
                intent.putExtra("dir", "/notice");
                startActivity(intent);
            }
            break;
        default:
            break;
        }
    }

}
