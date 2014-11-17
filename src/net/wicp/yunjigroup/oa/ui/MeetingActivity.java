package net.wicp.yunjigroup.oa.ui;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.NoticeList;
import net.wicp.yunjigroup.oa.models.NoticeListAdapter;
import net.wicp.yunjigroup.oa.models.NoticeSimple;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView.OnRefreshListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MeetingActivity extends BaseActivity implements OnClickListener, OnItemClickListener, OnRefreshListener{
    public static final String TAG = "会议通知";
    private PullToRefreshListView noticeListView = null;
    private TextView mTitleView = null;
    private ImageView mBackImg = null;
    private NoticeListAdapter mListAdapter = null;
    private NoticeList noticeList = null;
    private List<NoticeSimple> noticeSimples = new ArrayList<NoticeSimple>();
    private boolean isRefresh = false;
    
    class MeetingListTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!isRefresh)ProgressBarUtils.show(mContext,null);
        }
        
        @Override
        protected Void doInBackground( Void... params ) {
            noticeList = NetEngine.getInstance(mContext).getMeetingList();
            return null;
        }
        
        @Override
        protected void onPostExecute( Void result ) {
            if(!isRefresh){
                ProgressBarUtils.hide();
            }else{
                noticeListView.onRefreshComplete();
            }
            if(noticeList != null && noticeList.getNotices() != null){
                noticeSimples = noticeList.getNotices();
                mListAdapter.update(noticeSimples);
            }
            
            super.onPostExecute(result);
        }
        
    }
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);
        
        initViews();
        
        new MeetingListTask().execute();
    }
    
    @Override
    public void initViews() {
        mTitleView = (TextView) findViewById(R.id.title_tv);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mTitleView.setText(TAG);
        mBackImg.setOnClickListener(this);
        noticeListView = (PullToRefreshListView) findViewById(R.id.notice_list);
        noticeListView.setOnItemClickListener(this);
        noticeListView.setOnRefreshListener(this);
        mListAdapter = new NoticeListAdapter(mContext, noticeSimples);
        noticeListView.setAdapter(mListAdapter);
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;

        default:
            break;
        }
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View v, int position, long arg3 ) {
        startActivity(new Intent(mContext, MeetingDetailActivity.class).putExtra("noticeSimple", mListAdapter.getItem(position-1)));
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        new MeetingListTask().execute();
    }

}
