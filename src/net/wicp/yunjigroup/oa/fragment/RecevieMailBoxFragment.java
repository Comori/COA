package net.wicp.yunjigroup.oa.fragment;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.MailList;
import net.wicp.yunjigroup.oa.models.MailListAdapter;
import net.wicp.yunjigroup.oa.models.MailSimple;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.ui.MailDetailActivity;
import net.wicp.yunjigroup.oa.ui.MailUpdateListener;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.ProgressBarUtils;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView.OnRefreshListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class RecevieMailBoxFragment extends Fragment implements OnRefreshListener, OnItemClickListener{
    
    private PullToRefreshListView listView = null;
    private MailListAdapter mAdapter = null;
    private List<MailSimple> mailSimples = null;
    private boolean isRefresh = false;
    private MailUpdateListener updateListener = null;
    private boolean hasCreated = false;
    private View conteiView = null;
    private String searchTitle = "";
    
    class ReceviceBoxTask extends AsyncTask<String, Void, List<MailSimple>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!isRefresh)ProgressBarUtils.show(getActivity(),null);
        }
        
        @Override
        protected List<MailSimple> doInBackground( String... params ) {
            MailList list = NetEngine.getInstance(getActivity()).getReceviceMailList(params[0]);
            if(list != null)
            return list.getMailSimples();
            return null;
        }
        
        @Override
        protected void onPostExecute( List<MailSimple> result ) {
            super.onPostExecute(result);
            if(!isRefresh){
                ProgressBarUtils.hide();
            }else{
                listView.onRefreshComplete();
            }
            if(result != null){
                updateMailList(result);
            }
            if(updateListener != null){
                updateListener.onUpdate();
            }
        }
        
    }
    
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        mailSimples = new ArrayList<MailSimple>();
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
    	if(conteiView == null){
    		conteiView = inflater.inflate(R.layout.mail_list, container, false);
    	}
        return conteiView;
    }
    
    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);
        if(!hasCreated){
            hasCreated = true;
            listView = (PullToRefreshListView) getView().findViewById(R.id.mail_list);
            mAdapter = new MailListAdapter(getActivity(), mailSimples,true);
            listView.setAdapter(mAdapter);
            if(mailSimples.isEmpty())new ReceviceBoxTask().execute(searchTitle);
            listView.setOnRefreshListener(this);
            listView.setOnItemClickListener(this);
        }
    }
    
    public void updateMailList(List<MailSimple> mailSimples){
        if(mailSimples != null){
            if(TextUtils.isEmpty(searchTitle) && this.mailSimples != mailSimples){
                this.mailSimples.clear();
                this.mailSimples.addAll(mailSimples);
            }
                mAdapter.updateAll(mailSimples);
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        new ReceviceBoxTask().execute(searchTitle);
    }

    public void setUpdateListener( MailUpdateListener updateListener ) {
        this.updateListener = updateListener;
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View v, int position, long arg3 ) {
        MailSimple mailSimple = mAdapter.getItem(position-listView.getHeaderViewsCount());
        startActivity(new Intent(getActivity(), MailDetailActivity.class).putExtra("mailSimple", mailSimple).putExtra("isFromSendbox", false));
        mailSimple.setMarking(Contants.MAIL_READED);
    }

    public void searchMail(String search){
        if(!TextUtils.isEmpty(search)){
            searchTitle = search;
            new ReceviceBoxTask().execute(searchTitle);
        }else{
            updateMailList(mailSimples);
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    
}
