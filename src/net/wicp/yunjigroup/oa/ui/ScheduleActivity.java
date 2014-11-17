package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.fragment.ScheduleDoneFragmnt;
import net.wicp.yunjigroup.oa.fragment.ScheduleOverFragment;
import net.wicp.yunjigroup.oa.fragment.UnScheduleFragment;
import net.wicp.yunjigroup.oa.models.Schedules;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView.OnRefreshListener;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ScheduleActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener,OnRefreshListener{
    private ImageView mBackImg = null;
    private RadioGroup radioGroup = null;
    private UnScheduleFragment unScheduleFragment = null;
    private ScheduleDoneFragmnt doneFragmnt = null;
    private ScheduleOverFragment overFragment = null;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction transaction = null;
    private boolean isRefresh = false;
    
    class ScheduleTask extends AsyncTask<Void, Void, Schedules>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!isRefresh)ProgressBarUtils.show(mContext, null);
        }
        
        @Override
        protected Schedules doInBackground( Void... params ) {
            return NetEngine.getInstance(mContext).getWorkflowList();
        }
        
        @Override
        protected void onPostExecute( Schedules result ) {
            super.onPostExecute(result);
            if(!isRefresh){
                ProgressBarUtils.hide();
            }else{
                unScheduleFragment.refreshComplete();
                if(doneFragmnt != null)doneFragmnt.refreshComplete();
                if(overFragment != null)overFragment.refreshComplete();
            }
            if(result != null){
                unScheduleFragment.update(result.getUnSchedules());
               if(doneFragmnt == null) {
                   doneFragmnt = new ScheduleDoneFragmnt(result.getDoneSchedules());
               }else{
                   doneFragmnt.update(result.getDoneSchedules());
               }
               if(overFragment == null) {
                   overFragment = new ScheduleOverFragment(result.getOverSchedules());
               }else{
                   overFragment.update(result.getOverSchedules());
               }
            }
        }
        
    }
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        
        initViews();
        new ScheduleTask().execute();
    }
    
    @Override
    public void initViews() {
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.schedule_select_group);
        radioGroup.setOnCheckedChangeListener(this);
        
        unScheduleFragment = new UnScheduleFragment();
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.schedule_content, unScheduleFragment, "unSchedule");
        transaction.commit();
    }

    @Override
    public void onClick( View v ) {
        if(v.getId() == R.id.title_back){
            finish();
        }
    }

    @Override
    public void onCheckedChanged( RadioGroup btn, int checkedId ) {
        transaction = fragmentManager.beginTransaction();
        switch (checkedId) {
        case R.id.schedule_undoing:
            transaction.replace(R.id.schedule_content, unScheduleFragment, "unSchedule");
            break;
        case R.id.schedule_done:
            transaction.replace(R.id.schedule_content, doneFragmnt, "doneSchedule");
            break;
        case R.id.schedule_over:
            transaction.replace(R.id.schedule_content, overFragment, "overSchedule");
            break;
        default:
            break;
        }
        transaction.commit();
    }
    
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        new ScheduleTask().execute();
    }

}
