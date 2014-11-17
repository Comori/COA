package net.wicp.yunjigroup.oa.ui;

import java.io.IOException;
import java.io.StreamCorruptedException;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.HomeData;
import net.wicp.yunjigroup.oa.models.User;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity implements OnClickListener {

    private TextView nameTx = null;
    private TextView jobTx  = null;
    private User mUser = null;
    private TextView noticeTx,mailTx,meetingTx,workFlowTx,contactTx;
    private RelativeLayout contactLayout;
    private ScrollView scrollView = null;
    private boolean isLoading = false;
    
    class GetHomeDataTask extends AsyncTask<String, Void, HomeData>{
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading = true;
        }

        @Override
        protected HomeData doInBackground( String... arg0 ) {
            
            return NetEngine.getInstance(mContext).getHomeData(arg0[0]);
        }
        
        @Override
        protected void onPostExecute( HomeData result ) {
            super.onPostExecute(result);
            updateHomeData(result);
            isLoading = false;
        }
        
    }
    
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        Intent intent = getIntent();
        if(intent.getSerializableExtra(Contants.SP_USER) != null){
            mUser = (User) intent.getSerializableExtra(Contants.SP_USER);
        }else{
            try {
                mUser = (User) Utils.getObjFromSP(this, Contants.SP_USER);
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        initViews();
        
        new GetHomeDataTask().execute(mUser.getToken());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
       if(!isLoading) new GetHomeDataTask().execute(mUser.getToken());
    }

    @Override
    public void initViews() {
        scrollView = (ScrollView) findViewById(R.id.home_view);
        final int move[] = new int[2];
        scrollView.post(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ((ImageView)findViewById(R.id.home_oa)).getLocationInWindow(move);
                scrollView.scrollTo(0, move[1]-Utils.getStatusBarHeight(HomeActivity.this));
            }
        });
        nameTx = (TextView) findViewById(R.id.user_name);
        jobTx = (TextView) findViewById(R.id.user_job);
        if(mUser != null){
            nameTx.setText(mUser.getName());
            jobTx.setText(mUser.getDuty());
        }
        noticeTx = (TextView) findViewById(R.id.home_notice_unread);
        mailTx = (TextView) findViewById(R.id.home_mail_unread);
        meetingTx = (TextView) findViewById(R.id.home_meetting_unread);
        workFlowTx = (TextView) findViewById(R.id.home_schedule_unread);
        contactTx = (TextView) findViewById(R.id.home_contact_unread);
        contactLayout = (RelativeLayout) findViewById(R.id.home_contact);
        contactLayout.setOnClickListener(this);
        findViewById(R.id.home_notice).setOnClickListener(this);
        findViewById(R.id.home_setting).setOnClickListener(this);
        findViewById(R.id.home_mail).setOnClickListener(this);
        findViewById(R.id.home_meeting).setOnClickListener(this);
        findViewById(R.id.home_schedule).setOnClickListener(this);
    }
    
    private void updateHomeData(HomeData homeData){
        if(homeData == null){
            Toast.makeText(this, "数据加载失败，请重试！", Toast.LENGTH_SHORT).show();
            return;
        }
        String unRead = "未读";
        noticeTx.setText(homeData.getNoticeCount()+"条");
        mailTx.setText(homeData.getMessageCount()+unRead);
        meetingTx.setText(homeData.getMeetingCount()+"条");
        workFlowTx.setText(homeData.getWorkflowCount()+"条");
        contactTx.setText(homeData.getAddressCount()+"人");
        
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
        case R.id.home_notice:
            startActivity(new Intent(mContext, NoticeListActivity.class));
            break;
        case R.id.home_mail:
            startActivity(new Intent(mContext, MailActivity.class));
            break;
        case R.id.home_meeting:
            startActivity(new Intent(mContext, MeetingActivity.class));
            break;
        case R.id.home_schedule:
            startActivity(new Intent(mContext, ScheduleActivity.class));
            break;
        case R.id.home_contact:
            Intent intent = new Intent(this,ContactActivity.class);
            intent.putExtra("token", mUser.getToken());
            startActivity(intent);
            break;
        case R.id.home_setting:
            startActivity(new Intent(this, SettingActivity.class));
            break;
        default:
            break;
        }
    }
}
