package net.wicp.yunjigroup.oa.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.AddressGroup;
import net.wicp.yunjigroup.oa.models.ContactSimple;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.side.CharacterParser;
import net.wicp.yunjigroup.oa.side.PinyinComparator;
import net.wicp.yunjigroup.oa.side.SideBar;
import net.wicp.yunjigroup.oa.side.SideBar.OnTouchingLetterChangedListener;
import net.wicp.yunjigroup.oa.side.SortAdapter;
import net.wicp.yunjigroup.oa.side.SortModel;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends BaseActivity implements OnClickListener, OnItemClickListener{
	private TextView mTitleTv = null;
	private ImageView mBackImg = null;
	private ListView mListView = null;
	private SortAdapter sortAdapter = null;
	private SideBar sideBar = null;
	private TextView popTv = null;
	private String department = null;
	private EditText searchContentEdt = null;
	private ImageView searchClenImg = null;
	private TextView searchTv = null;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser = null;
	private List<SortModel> sourceDateList = null;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator = null;
	
	class ContactListTask extends AsyncTask<String, Void, List<ContactSimple>>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ProgressBarUtils.show(mContext, null);
		}
		
		@Override
		protected List<ContactSimple> doInBackground(String... params) {
		    if(!TextUtils.isEmpty(params[0])){
               return NetEngine.getInstance(mContext).getAddressListUser(department, params[0]);
            }else{
                return NetEngine.getInstance(mContext).getAddressListUser(department, "");
            }
		}
		
		@Override
		protected void onPostExecute(List<ContactSimple> result) {
			super.onPostExecute(result);
			ProgressBarUtils.hide();
			if(result != null){
			    if(sortAdapter == null){
			        sourceDateList = Utils.filledData(result);
			        // 根据a-z进行排序源数据
			        Collections.sort(sourceDateList, pinyinComparator);
			        sortAdapter = new SortAdapter(mContext, sourceDateList);
			        mListView.setAdapter(sortAdapter);
			    }else{
			        List<SortModel> updateDatas =  Utils.filledData(result);
			        Collections.sort(updateDatas, pinyinComparator);
			        sortAdapter.updateListView(updateDatas);
			    }
			}
		}
		
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		initViews();
		new ContactListTask().execute("");
	}
	
	@Override
	public void initViews() {
		AddressGroup addressGroup = (AddressGroup) getIntent().getSerializableExtra("contactGroup");
		department = addressGroup.getGroupName();
		mBackImg = (ImageView) findViewById(R.id.title_back);
		mBackImg.setOnClickListener(this);
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText(department+"("+addressGroup.getCount()+")");
		mListView = (ListView) findViewById(R.id.contact_list_listview);
		mListView.setOnItemClickListener(this);
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		popTv = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(popTv);
		searchContentEdt = (EditText) findViewById(R.id.search_edt);
		searchClenImg = (ImageView) findViewById(R.id.search_clean);
        searchClenImg.setOnClickListener(this);
        searchTv = (TextView) findViewById(R.id.search_ok);
        searchTv.setOnClickListener(this);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				int position = sortAdapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					mListView.setSelection(position);
				}
			}
		});
		
		searchContentEdt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
				if(!TextUtils.isEmpty(s) && searchClenImg.getVisibility() == View.INVISIBLE){
		            searchClenImg.setVisibility(View.VISIBLE);
		            searchTv.setVisibility(View.VISIBLE);
		        }
		        if(TextUtils.isEmpty(s)){
		            searchClenImg.setVisibility(View.INVISIBLE);
		            searchTv.setVisibility(View.GONE);
		        }
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.search_clean:
			searchContentEdt.setText("");
			break;
		case R.id.search_ok:
		    String search = searchContentEdt.getText().toString();
		    if(sortAdapter.getCount() == 0 && !TextUtils.isEmpty(search)){
		        new ContactListTask().execute(search);
		    }
		    break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
		SortModel model = sortAdapter.getItem(position);
		startActivity(new Intent(mContext, ContactDetailActivity.class).putExtra("id", model.getId()).putExtra("name", model.getName()));
	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = sourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : sourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		sortAdapter.updateListView(filterDateList);
	}
}
