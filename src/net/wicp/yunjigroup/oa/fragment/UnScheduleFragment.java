package net.wicp.yunjigroup.oa.fragment;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.ScheduleSimple;
import net.wicp.yunjigroup.oa.models.ScheludeListAdapter;
import net.wicp.yunjigroup.oa.ui.ScheduleActivity;
import net.wicp.yunjigroup.oa.ui.ScheduleDetailActivity;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView;
import net.wicp.yunjigroup.oa.view.PullToRefreshListView.OnRefreshListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class UnScheduleFragment extends Fragment implements OnItemClickListener{
    private PullToRefreshListView listView = null;
    private ScheludeListAdapter mAdapter = null;
    private List<ScheduleSimple> simples = null;
    private OnRefreshListener refreshListener = null;
    
    public UnScheduleFragment() {
        simples = new ArrayList<ScheduleSimple>();
    }
    
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        refreshListener = (ScheduleActivity)getActivity();
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.schedule_list, container,false);
        listView = (PullToRefreshListView) view.findViewById(R.id.schedule_list);
        listView.setOnRefreshListener(refreshListener);
        return view;
    }
    
    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ScheludeListAdapter(getActivity(), simples);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }
    
    public void update(List<ScheduleSimple> simples) {
        if(simples != null){
            this.simples =simples;
            mAdapter.updateData(simples);
        }
    }
    
    @Override
    public void onItemClick( AdapterView<?> parent, View v, int position, long arg3 ) {
        ScheduleSimple simple = mAdapter.getItem(position-1);
        startActivity(new Intent(getActivity(), ScheduleDetailActivity.class).putExtra("url", simple.getUrl()).putExtra("title", simple.getTitle()));
    }
    
    public void refreshComplete(){
        if(listView != null){
            listView.onRefreshComplete();
        }
    }
}
