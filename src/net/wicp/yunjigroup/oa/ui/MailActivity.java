package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.fragment.RecevieMailBoxFragment;
import net.wicp.yunjigroup.oa.fragment.SendMailBoxFragment;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MailActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener, MailUpdateListener, TextWatcher{
    
    private FragmentManager fragmentManager = null;
    
    private FragmentTransaction fragmentTransaction = null;
    
    private RecevieMailBoxFragment recevieMailBoxFragment = null;
    private SendMailBoxFragment sendMailBoxFragment = null;
    
    private ImageView mBackImg = null;
    private RadioGroup radioGroup = null;
    private EditText searchEdt = null;
    private TextView searchTv = null;
    private ImageView searchCleanImg = null;
    private ImageView sendImg = null;
    
    private TextView updateInfoTv = null;
    private Animation animation = null;
    private boolean isRecevieboxCheched = true;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        initViews();
    }

    @Override
    public void initViews() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.search_ok);
        mBackImg = (ImageView) findViewById(R.id.title_back);
        mBackImg.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.mail_select_group);
        radioGroup.setOnCheckedChangeListener(this);
        updateInfoTv = (TextView) findViewById(R.id.mail_updateInfoTv);
        searchEdt = (EditText) findViewById(R.id.search_edt);
        searchTv = (TextView) findViewById(R.id.search_ok);
        searchTv.setOnClickListener(this);
        searchCleanImg = (ImageView) findViewById(R.id.search_clean);
        searchCleanImg.setOnClickListener(this);
        searchEdt.addTextChangedListener(this);
        searchEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                searchTv.setVisibility(hasFocus?View.VISIBLE:View.INVISIBLE);
                if(hasFocus){
                    searchTv.startAnimation(animation);
                }
            }
            
        });
        sendImg = (ImageView) findViewById(R.id.mail_sendImg);
        sendImg.setOnClickListener(this);
        
        recevieMailBoxFragment = new RecevieMailBoxFragment();
        sendMailBoxFragment = new SendMailBoxFragment();
        recevieMailBoxFragment.setUpdateListener(this);
        sendMailBoxFragment.setUpdateListener(this);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mail_content, recevieMailBoxFragment, "recevicebox");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.search_clean:
            searchEdt.setText("");
//            recevieMailBoxFragment.searchMail("");
//            sendMailBoxFragment.searchMail("");
            break;
        case R.id.search_ok:
            String input = searchEdt.getText().toString();
            if(!TextUtils.isEmpty(input)){
                if(isRecevieboxCheched){
                    recevieMailBoxFragment.searchMail(input);
                }else{
                    sendMailBoxFragment.searchMail(input);
                }
                
            }
            break;
        case R.id.mail_sendImg:
            startActivity(new Intent(mContext, SendMailActivity.class).putExtra("act", Contants.MAIL_SEND));
            break;
        default:
            break;
        }
    }

    @Override
    public void onCheckedChanged( RadioGroup group, int checkedId ) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (checkedId) {
        case R.id.mail_recevieBox:
            isRecevieboxCheched = true;
            fragmentTransaction.replace(R.id.mail_content, recevieMailBoxFragment);
            break;
        case R.id.mail_sendBox:
            isRecevieboxCheched = false;
            fragmentTransaction.replace(R.id.mail_content, sendMailBoxFragment);
            break;

        default:
            break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onUpdate() {
        updateInfoTv.setText("更新于:"+Utils.getNowDate());
    }

    @Override
    public void afterTextChanged( Editable s ) {
        System.out.println("after");
    }

    @Override
    public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
        System.out.println("bef");
    }

    @Override
    public void onTextChanged( CharSequence s, int start, int before, int count ) {
        if(!TextUtils.isEmpty(s) && searchCleanImg.getVisibility() == View.INVISIBLE){
            searchCleanImg.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(s)){
            searchCleanImg.setVisibility(View.INVISIBLE);
            recevieMailBoxFragment.searchMail("");
            sendMailBoxFragment.searchMail("");
        }
    }
    
    
    

}
