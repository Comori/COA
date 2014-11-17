package net.wicp.yunjigroup.oa.view;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.AddressGroup;
import net.wicp.yunjigroup.oa.models.AddressGroupAdpater;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AddressGroupFragment extends ListFragment{
    private AddressGroupAdpater adpater = null;
    private List<AddressGroup> addressGroups = null;
    
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        addressGroups = new ArrayList<AddressGroup>();
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.group_list, container, false);
        adpater = new AddressGroupAdpater(getActivity(), addressGroups);
        setListAdapter(adpater);
        return view;
    }
    
    public void update(List<AddressGroup> datas){
        addressGroups.addAll(datas);
        adpater.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick( ListView l, View v, int position, long id ) {
        
        super.onListItemClick(l, v, position, id);
    }
}
