package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements OnClickListener{
    private TextView mTitlTv;
    private ImageView mBackImg;
    private TextView aboutTv;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        initViews();
    }
    
    @Override
    public void initViews() {
        mTitlTv = (TextView) findViewById(R.id.title_tv);
        mTitlTv.setText("关于我们");
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        aboutTv = (TextView) findViewById(R.id.about_usTv);
        
        String content = Utils.getAboutUsContent(mContext);
        if(!TextUtils.isEmpty(content)){
            aboutTv.setText(Html.fromHtml(content));
        }
    }

    @Override
    public void onClick( View arg0 ) {
        finish();
    }

}
