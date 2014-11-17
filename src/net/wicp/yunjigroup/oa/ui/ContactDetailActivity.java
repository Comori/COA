package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.Contact;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailActivity extends BaseActivity implements OnClickListener{
    private String id = null;
    private String name = null;
    
    private TextView mTitleTv = null;
    private ImageView mBackImg = null;
    private TextView nameTv,mobileTv,tel1Tv,tel2Tv,emailTv,departmentTv;
    private ImageView mobileCallImg,mobileSmsImg,tel1CallImg,tel2CallImg,emailImg;
    private Contact contact = null;
    
    class ContactDetailTask extends AsyncTask<Void, Void, Contact>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarUtils.show(mContext, null);
        }
        
        @Override
        protected Contact doInBackground( Void... params ) {
            return NetEngine.getInstance(mContext).getAddressDetail(id, name);
        }
        
        @Override
        protected void onPostExecute( Contact result ) {
            super.onPostExecute(result);
            ProgressBarUtils.hide();
            if(result != null){
                contact = result;
                assembleView();
            }
        }
        
    }
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        initViews();
        new ContactDetailTask().execute();
    }
    
    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        mTitleTv.setText("详情");
        
        nameTv = (TextView) findViewById(R.id.contact_detail_name);
        mobileTv = (TextView) findViewById(R.id.contact_detail_mobile);
        tel1Tv = (TextView) findViewById(R.id.contact_detail_phone1);
        tel2Tv = (TextView) findViewById(R.id.contact_detail_phone2);
        emailTv = (TextView) findViewById(R.id.contact_detail_email);
        departmentTv = (TextView) findViewById(R.id.contact_detail_department);
        mobileCallImg = (ImageView) findViewById(R.id.contact_detail_mobile_call);
        mobileCallImg.setOnClickListener(this);
        mobileSmsImg = (ImageView) findViewById(R.id.contact_detail_mobile_sms);
        mobileSmsImg.setOnClickListener(this);
        tel1CallImg = (ImageView) findViewById(R.id.contact_detail_phone1_call);
        tel1CallImg.setOnClickListener(this);
        tel2CallImg = (ImageView) findViewById(R.id.contact_detail_phone2_call);
        tel2CallImg.setOnClickListener(this);
        emailImg = (ImageView) findViewById(R.id.contact_detail_email_call);
        emailImg.setOnClickListener(this);
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.contact_detail_mobile_call:
            if(!TextUtils.isEmpty(contact.getMobile())){
                Utils.callPhone(mContext, contact.getMobile());
            }
            break;
        case R.id.contact_detail_mobile_sms:
            if(!TextUtils.isEmpty(contact.getMobile())){
                Utils.sendSms(mContext, contact.getMobile());
            }
            break;
        case R.id.contact_detail_phone1_call:
            if(!TextUtils.isEmpty(contact.getTel1())){
                Utils.callPhone(mContext, contact.getTel1());
            }
            break;
        case R.id.contact_detail_phone2_call:
            if(!TextUtils.isEmpty(contact.getTel2())){
                Utils.callPhone(mContext, contact.getTel2());
            }
            break;
        case R.id.contact_detail_email_call:
            Intent intent = new Intent(mContext, SendMailActivity.class);
            intent.putExtra("recevier", contact.getName());
            intent.putExtra("act", Contants.MAIL_SEND);
            startActivity(intent);
            break;

        default:
            break;
        }
    }

    private void assembleView(){
        nameTv.setText(contact.getName());
        mobileTv.setText(contact.getMobile());
        tel1Tv.setText(contact.getTel1());
        tel2Tv.setText(contact.getTel2());
        emailTv.setText(contact.getEmail());
        departmentTv.setText(contact.getDepartment());
    }
}
