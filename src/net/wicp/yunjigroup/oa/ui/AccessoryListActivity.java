package net.wicp.yunjigroup.oa.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.AccessoryListAdapter;
import net.wicp.yunjigroup.oa.net.Request;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
import net.wicp.yunjigroup.oa.utils.Utils.FileDownListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AccessoryListActivity extends BaseActivity implements OnClickListener,
        OnItemClickListener {
    public static final String   TAG               = "附件";

    private TextView             mTitleTv          = null;
    private ImageView            mBackImg          = null;
    private ListView             accessoryListView = null;
    private AccessoryListAdapter mAdapter          = null;
    private String dir = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessory_list);
        initViews();
        initDatas();
    }

    @Override
    public void initViews() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mTitleTv.setText(TAG);
        mBackImg.setOnClickListener(this);
        accessoryListView = (ListView) findViewById(R.id.accessory_list);
        accessoryListView.setOnItemClickListener(this);
    }

    private void initDatas() {
        Intent intent = getIntent();
        List<String> paths = intent.getStringArrayListExtra("paths");
        dir = intent.getStringExtra("dir");
        if (paths == null) {
            paths = new ArrayList<String>();
        }
        mAdapter = new AccessoryListAdapter(mContext, paths);
        accessoryListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick( View v ) {
        if(v.getId() == R.id.title_back){
            finish();
        }
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View v, int position, long arg3 ) {
        String path = mAdapter.getItem(position);
        final String url = Utils.urlEncode(Request.HOST + path);
        final String filePath = Contants.CACHE_PATH + dir ;
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
}
