package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.BaseData;
import net.wicp.yunjigroup.oa.models.MailDetail;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SendMailActivity extends BaseActivity implements OnClickListener{
    private EditText contentEdt = null;
    private EditText subjectTv = null;
    private MailDetail mailDetail = null;
    private String sendTypeStr = null;
    private ImageView addUserImg = null;
    private String sjr = null;
    private TextView recevierTv = null;
    private ImageView mBackImg = null;
    private TextView mTitleTv = null;
    private ImageView sendImg = null;
    
    class SendMailTask extends AsyncTask<Void, Void, Boolean>{
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		ProgressBarUtils.show(mContext, "正在发送...");
    	}
    	
		@Override
		protected Boolean doInBackground(Void... arg0) {
			if(sendTypeStr.equals(Contants.MAIL_SEND_ACT)){
				return NetEngine.getInstance(mContext).sendMail("0","0",sendTypeStr, subjectTv.getText().toString(), recevierTv.getText().toString(), contentEdt.getText().toString());
			}else{
				return NetEngine.getInstance(mContext).sendMail(mailDetail.getCard(),mailDetail.getId(),sendTypeStr, subjectTv.getText().toString(), recevierTv.getText().toString(), contentEdt.getText().toString());
			}
		}
    	
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			ProgressBarUtils.hide();
			if(result){
				Toast.makeText(mContext, "发送成功!", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(mContext, "发送失败!", Toast.LENGTH_SHORT).show();
			}
		}
    }
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mail);
        initViews();
        initDatas();
    }

    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    public void initViews() {
        contentEdt = (EditText) findViewById(R.id.send_mail_contentEdt);
        subjectTv = (EditText) findViewById(R.id.send_mail_subject);
        addUserImg = (ImageView) findViewById(R.id.send_mail_addUserImg);
        addUserImg.setOnClickListener(this);
        recevierTv = (TextView) findViewById(R.id.send_mail_receiveUsers);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
       mBackImg.setOnClickListener(this);
       sendImg = (ImageView) findViewById(R.id.title_send);
       sendImg.setOnClickListener(this);
    }
    
    private void initDatas() {
        Intent intent = getIntent();
        int act = intent.getIntExtra("act",-1);
        if(act == Contants.MAIL_SEND){
            String re = intent.getStringExtra("recevier");
            sendTypeStr = Contants.MAIL_SEND_ACT;
            mTitleTv.setText(getString(R.string.mail_send_title));
            if(!TextUtils.isEmpty(re)){
                recevierTv.setText(re);
            }
        }else{
            sendTypeStr = act==Contants.MAIL_REPLY?Contants.MAIL_REPLY_ACT:Contants.MAIL_FORWORD_ACT;
            
            mailDetail = (MailDetail) intent.getSerializableExtra("mailDetail");
            subjectTv.setText(sendTypeStr+":"+mailDetail.getTitle());
           if(act == Contants.MAIL_REPLY){
        	   recevierTv.setText(mailDetail.getSendUser());
        	   mTitleTv.setText(getString(R.string.mail_reply_title));
           }else{
        	   mTitleTv.setText(getString(R.string.mail_forword_title));
           }
        }
        contentEdt.setText(Html.fromHtml(Utils.createDefaultTextForSendMail(act, mailDetail)));
    }

    @Override
    public void onClick( View v ) {
    	switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.send_mail_addUserImg:
			Utils.clearReceviers();
			startActivityForResult(new Intent(mContext, MailContactActivity.class),0);
			break;
		case R.id.title_send:
		    if(!TextUtils.isEmpty(recevierTv.getText().toString()) && !TextUtils.isEmpty(subjectTv.getText().toString())){
		        new SendMailTask().execute();
		    }else{
		        Toast.makeText(mContext, "收件人和主题不能为空！", Toast.LENGTH_SHORT).show();
		    }
			break;
		default:
			break;
		}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	StringBuffer s = new StringBuffer();
    	if(resultCode == Contants.ACTIVITY_RESULT_OK_CODE){
    		if(sendTypeStr.equals(Contants.MAIL_REPLY_ACT)){
    			s .append(mailDetail.getSendUser());
    			if(!Utils.getMailRecevier().isEmpty()){
    				s.append(",");
    			}
    		}
    		sjr = s.toString() + Utils.getMailRecevier();
    	}
    	recevierTv.setText(sjr);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	Utils.releaseReceviers();
    }
}
