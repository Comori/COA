package net.wicp.yunjigroup.oa.ui;

import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.AddressGroup;
import net.wicp.yunjigroup.oa.models.AddressGroupAdpater;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
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


public class ContactActivity extends BaseActivity implements OnClickListener, OnItemClickListener{
    private TextView mTitleTv = null;
    private ImageView mBackImg = null;
    private ListView listView = null;
    private AddressGroupAdpater adpater = null;
    private EditText searchEdt = null;
    private ImageView searchCleanImg = null;
    private TextView searchTv = null;
    
    class ContactGroupTask extends AsyncTask<String, Void, List<AddressGroup>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarUtils.show(mContext, null);
        }
        
        @Override
        protected List<AddressGroup> doInBackground( String... params ) {
            if(!TextUtils.isEmpty(params[0])){
                NetEngine.getInstance(mContext).getAddressListUser("全部人员", params[0]);
                return null;
            }else{
                return NetEngine.getInstance(mContext).getAddressList();
            }
        }
        
        @Override
        protected void onPostExecute( List<AddressGroup> result ) {
            super.onPostExecute(result);
            ProgressBarUtils.hide();
            if(result != null){
                adpater = new AddressGroupAdpater(mContext, result);
                listView.setAdapter(adpater);
            }
        }
    }
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_group);
        initViews();
        new ContactGroupTask().execute("");
    }
    
    @Override
    public void initViews() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText("通讯录");
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.contact_list);
        listView.setOnItemClickListener(this);
        searchCleanImg = (ImageView) findViewById(R.id.search_clean);
        searchCleanImg.setOnClickListener(this);
        searchEdt = (EditText) findViewById(R.id.search_edt);
        searchEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange( View arg0, boolean hasFocus ) {
                searchTv.setVisibility(hasFocus?View.VISIBLE:View.INVISIBLE);
            }
        });
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                if(!TextUtils.isEmpty(s) && searchCleanImg.getVisibility() == View.INVISIBLE){
                    searchCleanImg.setVisibility(View.VISIBLE);
                }
                if(TextUtils.isEmpty(s)){
                    searchCleanImg.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
            }
            @Override
            public void afterTextChanged( Editable s ) {
            }
        });
        searchTv = (TextView) findViewById(R.id.search_ok);
        searchTv.setOnClickListener(this);
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.search_clean:
            searchEdt.setText("");
            break;
        case R.id.search_ok:
            String search = searchEdt.getText().toString();
            if(!TextUtils.isEmpty(search)){
                new ContactGroupTask().execute(search);
            }
            break;
        default:
            break;
        }
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View v, int position, long arg3 ) {
        final AddressGroup addressGroup = adpater.getItem(position);
        startActivity(new Intent(mContext, ContactListActivity.class).putExtra("contactGroup", addressGroup));
    }
    
    
}
