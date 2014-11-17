package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author chenqiang
 * @2014年10月21日
 * @TODO
 */
public class FeedbackActivity extends BaseActivity implements OnClickListener{
    
    private static final String TAG = "意见反馈";
    private TextView mTitleTv,mRightTv;
    private ImageView mTitleBack = null;
    private EditText contentEdt = null;
    private boolean isSend = false;
    
    class SendFeedback extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarUtils.show(mContext, "");
        }
        
        @Override
        protected Void doInBackground( String... params ) {
            NetEngine.getInstance(mContext).sendFeedback(params[0], "");
            return null;
        }
        
        @Override
        protected void onPostExecute( Void result ) {
            super.onPostExecute(result);
            ProgressBarUtils.hide();
            isSend = true;
            Toast.makeText(FeedbackActivity.this,"已成功反馈意见！", Toast.LENGTH_SHORT).show();
        }
        
        
    }
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        initViews();
    }

    @Override
    public void initViews() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mRightTv = (TextView) findViewById(R.id.right_titleTv);
        mTitleBack = (ImageView) findViewById(R.id.title_back);
        contentEdt = (EditText) findViewById(R.id.feedback_contentEdt);
        mTitleTv.setText(TAG);
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText(R.string.send);
        mRightTv.setOnClickListener(this);
        mTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick( View arg0 ) {
        if(arg0.getId() == R.id.right_titleTv){
            String content = contentEdt.getText().toString().trim();
            if(!TextUtils.isEmpty(content) && !isSend){
                new SendFeedback().execute(content);
                finish();
            }else{
                if(isSend){
                    Toast.makeText(this,"请稍后再试！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, getString(R.string.fedback_empty_notice), Toast.LENGTH_SHORT).show();
                }
            }
            
        }else if(arg0.getId() == R.id.title_back){
            finish();
        }
    }

}
