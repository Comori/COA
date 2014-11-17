package net.wicp.yunjigroup.oa.ui;

import net.wicp.yunjigroup.oa.utils.ActivitesStack;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public abstract class BaseActivity extends Activity{
    
    protected Context mContext = null;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivitesStack.getInstance().push(this);
        mContext = this;
    }
    
    public abstract void initViews();
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitesStack.getInstance().pop(this);
    }
    
}
