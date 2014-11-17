package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressGroupAdpater extends BaseAdapter{
    
    private List<AddressGroup> groups = null;
    
    private Context mContext = null;
    
    public AddressGroupAdpater(Context context) {
        this.mContext = context;
        groups = new ArrayList<AddressGroup>();
    }
    
    public AddressGroupAdpater(Context context,List<AddressGroup> groups){
        this.mContext = context;
        if(groups == null) {
            this.groups = new ArrayList<AddressGroup>();
        }
        else{
            this.groups = groups;
        }
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public AddressGroup getItem( int position ) {
        return groups.get(position);
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_group_item, null);
            holder = new ViewHolder();
            holder.countTv = (TextView) convertView.findViewById(R.id.contact_group_countTv);
            holder.groupNameTv = (TextView) convertView.findViewById(R.id.contact_group_nameTv);
            convertView.setTag(holder);;
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AddressGroup group = groups.get(position);
        holder.countTv.setText(group.getCount() + "人");
        holder.groupNameTv.setText(group.getGroupName());
        
        return convertView;
    }
    
    static class ViewHolder{
        private TextView groupNameTv;
        private TextView countTv;
    }

}
