package net.wicp.yunjigroup.oa.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.Cate;
import net.wicp.yunjigroup.oa.models.CateList;
import net.wicp.yunjigroup.oa.models.MailContactAdapter;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;

public class MailContactActivity extends BaseActivity implements OnItemClickListener, OnClickListener{
	private ListView listView = null;
	private MailContactAdapter madAdapter = null;
	private List<Cate> parentCates = null;
	private Stack<Cate> catesStack = null;
	private boolean isParent = true;
	private ImageView mBackImg = null;
	private TextView mTitleTv = null;
	private static final String mtitle = "选择人员";
	private TextView mTitleRightTv = null;

	class MailContactTask extends AsyncTask<Void, Void, CateList>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ProgressBarUtils.show(mContext,null);
		}
		
		@Override
		protected CateList doInBackground(Void... arg) {
			
			return NetEngine.getInstance(mContext).getOrgs();
		}
		
		@Override
		protected void onPostExecute(CateList result) {
			super.onPostExecute(result);
			ProgressBarUtils.hide();
			if(result != null){
				parentCates = result.getParentCates();
				madAdapter.update(result.getParentCates(),null);
			}
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_contact);
		initViews();
		initData();
		new MailContactTask().execute();
	}
	
	private void initData() {
		parentCates = new ArrayList<Cate>();
		catesStack = new Stack<Cate>();
		madAdapter = new MailContactAdapter(mContext, parentCates,null);
		listView.setAdapter(madAdapter);
		listView.setOnItemClickListener(this);
		mBackImg.setOnClickListener(this);
	}

	@Override
	public void initViews() {
		listView = (ListView) findViewById(R.id.mail_contactList);
		mBackImg = (ImageView) findViewById(R.id.title_back);
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText(mtitle);
		mTitleRightTv = (TextView) findViewById(R.id.right_titleTv);
		mTitleRightTv.setVisibility(View.VISIBLE);
		mTitleRightTv.setText("确定");
		mTitleRightTv.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
		Object object = madAdapter.getItem(position);
		if(object instanceof Cate){
			Cate parentCate = (Cate) object;
			if((parentCate.getCateChiildren().size() + parentCate.getNames().size()) > 0){
				isParent = false;
				madAdapter = new MailContactAdapter(mContext, parentCate.getCateChiildren(), parentCate);
				listView.setAdapter(madAdapter);
				catesStack.push(parentCate);
				mTitleTv.setText(parentCate.getCateName());
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			if(isParent){
				finish();
			}else{
				Cate cate = null;
				if(!catesStack.isEmpty()) catesStack.pop();
				if(!catesStack.isEmpty()){
					cate = catesStack.peek();
				}
				if(cate == null){
					isParent = true;
					madAdapter.update(parentCates, null);
					mTitleTv.setText(mtitle);
				}else{
					madAdapter.update(cate.getCateChiildren(), cate);
					mTitleTv.setText(cate.getCateName());
				}
			}
			break;
		case R.id.right_titleTv:
		    this.setResult(Contants.ACTIVITY_RESULT_OK_CODE);
            finish();
		    break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
	    if(isParent){
            finish();
        }else{
            Cate cate = null;
            if(!catesStack.isEmpty()) catesStack.pop();
            if(!catesStack.isEmpty()){
                cate = catesStack.peek();
            }
            if(cate == null){
                isParent = true;
                madAdapter.update(parentCates, null);
                mTitleTv.setText(mtitle);
            }else{
                madAdapter.update(cate.getCateChiildren(), cate);
                mTitleTv.setText(cate.getCateName());
            }
        }
	}
}
