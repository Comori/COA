package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.utils.ActivitesStack;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @author chenqiang
 * @2014年10月21日
 * @TODO
 */
public class SettingActivity extends BaseActivity implements OnClickListener{
    
    private static final String TAG = "设置";
    
    private TextView mTitleTv = null;
    private ImageView mBackImg = null;
    private LinearLayout aboutUsLayout,feedbackLayout,gradeLayout;
    private Button exitLoginBtn = null; 
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initViews();
    }

    @Override
    public void initViews() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(TAG);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        aboutUsLayout = (LinearLayout) findViewById(R.id.setting_aboutus_layout);
        feedbackLayout = (LinearLayout) findViewById(R.id.setting_feedback_layout);
        gradeLayout = (LinearLayout) findViewById(R.id.setting_grade_layout);
        exitLoginBtn = (Button) findViewById(R.id.exit_loginBtn);
        aboutUsLayout.setOnClickListener(this);
        feedbackLayout.setOnClickListener(this);
        gradeLayout.setOnClickListener(this);
        exitLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick( View v ) {
        Intent intent = null;
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.setting_aboutus_layout:
            intent = new Intent(mContext, AboutActivity.class);
            break;
        case R.id.setting_feedback_layout:
            intent = new Intent(this, FeedbackActivity.class);
            break;
        case R.id.setting_grade_layout:
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
            break;
        case R.id.exit_loginBtn:
            Utils.setLoginState(mContext, false);
            Utils.clearToken(mContext);
            ActivitesStack.getInstance().popAll();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            break;
        default:
            break;
        }
        if(intent != null)startActivity(intent);
    }

}
