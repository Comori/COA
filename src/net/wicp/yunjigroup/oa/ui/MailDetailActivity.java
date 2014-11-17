package net.wicp.yunjigroup.oa.ui;

import java.io.File;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.MailDetail;
import net.wicp.yunjigroup.oa.models.MailSimple;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.net.Request;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
import net.wicp.yunjigroup.oa.utils.Utils.FileDownListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MailDetailActivity extends BaseActivity implements OnClickListener{
	public static final String TAG = "邮件详情";
	private TextView mTitleTV;
	private ImageView mBackImg;
	private TextView titleTv,sendUserTv,sendTimeTv,accessoryCountsTv;
	private TextView contentTv;
	private LinearLayout accessoresLayout;
	private MailSimple mailSimple = null;
	private MailDetail detail = null;
	private TextView accessoryTv,userTypeTv;
	private boolean isFromSendbox = false;
	private ImageView replyImg,forwordImg;
	
	class MailDetailTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ProgressBarUtils.show(mContext,null);
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
		    if(isFromSendbox){
		        detail = NetEngine.getInstance(mContext).getSendMailDetail(mailSimple.getCard(), mailSimple.getId());
		    }else{
		        detail = NetEngine.getInstance(mContext).getRecevieMailDetail(mailSimple.getCard(), mailSimple.getId());
		        
		    }
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ProgressBarUtils.hide();
			if(detail != null){
				contentTv.setText(detail.getContent());
				createAccessoryLayout(detail.getAccessoryPaths());
			}
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_detail);
		initViews();
		initData();
		new MailDetailTask().execute();
	}
	
	@Override
	public void initViews() {
		mTitleTV = (TextView) findViewById(R.id.title_tv);
		mBackImg = (ImageView) findViewById(R.id.title_back);
		mTitleTV.setText(TAG);
		mBackImg.setOnClickListener(this);
		titleTv = (TextView) findViewById(R.id.mail_detail_titleTV);
		sendUserTv = (TextView) findViewById(R.id.mail_detail_sendUserName);
		sendTimeTv = (TextView) findViewById(R.id.mail_detail_sendTimeTv);
		accessoryCountsTv = (TextView) findViewById(R.id.mail_detail_fujian);
		contentTv = (TextView) findViewById(R.id.mail_detail_content);
		accessoresLayout = (LinearLayout) findViewById(R.id.mail_detail_accessorysLayout);
		accessoryTv = (TextView) findViewById(R.id.mail_detail_fujian1);
		userTypeTv = (TextView) findViewById(R.id.mail_detail_sendUserTv);
		replyImg = (ImageView) findViewById(R.id.mail_detail_reply);
		forwordImg = (ImageView) findViewById(R.id.mail_detail_repeat);
		replyImg.setOnClickListener(this);
		forwordImg.setOnClickListener(this);
	}
	
	private void initData(){
		mailSimple = (MailSimple) getIntent().getSerializableExtra("mailSimple");
		isFromSendbox = getIntent().getBooleanExtra("isFromSendbox", false);
		titleTv.setText(mailSimple.getTitle());
		if(isFromSendbox){
		    userTypeTv.setText(getString(R.string.recevie_name));
		    sendUserTv.setText(mailSimple.getShoujianren());
		    sendTimeTv.setText(mailSimple.getShijian());
		}else{
		    userTypeTv.setText(getString(R.string.send_name));
		    sendUserTv.setText(mailSimple.getSendUser());
		    sendTimeTv.setText(mailSimple.getSendTime());
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.mail_detail_reply:
            Intent intent = new Intent(mContext, SendMailActivity.class);
            intent.putExtra("act", Contants.MAIL_REPLY);
            intent.putExtra("mailDetail", detail);
            startActivity(intent);
            break;
        case R.id.mail_detail_repeat:
        	Intent intent1 = new Intent(mContext, SendMailActivity.class);
            intent1.putExtra("act", Contants.MAIL_FORWORD);
            intent1.putExtra("mailDetail", detail);
            startActivity(intent1);
            break;
        default:
            break;
        }
	}
	
	private void createAccessoryLayout(List<String> paths){
		if(paths != null && !paths.isEmpty()){
		    accessoryTv.setVisibility(View.VISIBLE);
		    accessoryCountsTv.setText(paths.size()+"");
			View view = null;
			TextView nameTv;
			ImageView accessoryImg ;
			for(String path : paths){
				view = LayoutInflater.from(mContext).inflate(R.layout.mail_detail_accessory_item, null);
				nameTv = (TextView) view.findViewById(R.id.accessory_name);
				accessoryImg = (ImageView) view.findViewById(R.id.accessory_img);
				nameTv.setText(Utils.getFileNameFromUrl(path));
				accessoryImg.setImageResource(Utils.getAccessoryIconId(path));
				view.setTag(path);
				accessoresLayout.addView(view);
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String path = (String) v.getTag();
						final String url = Utils.urlEncode(Request.HOST + path);
						final String filePath = Contants.CACHE_PATH + "/mail" ;
						final String fileName = Utils.getFileNameFromUrl(path);
						Utils.downFile(url, filePath, fileName, new FileDownListener() {
                            @Override
                            public void onStart() {
                                ProgressBarUtils.show(mContext,null);
                            }
                            
                            @Override
                            public void onFaile() {
                                
                            }
                            
                            @Override
                            public void onComplete() {
                                ProgressBarUtils.hide();
                                Uri uri = Uri.fromFile(new File(filePath,fileName)); 
                                String mime = Utils.getDataType(Utils.getPostfix(fileName));
                                Utils.openAccessory(mContext, uri, mime);
                            }
                        });
					}
				});
			}
		}
	}

}
