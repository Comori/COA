package net.wicp.yunjigroup.oa.models;

import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccessoryListAdapter extends BaseAdapter{
    private Context mContext = null;
    private List<String> paths = null;
    
    public AccessoryListAdapter(Context context,List<String> paths){
        this.mContext = context;
        this.paths = paths;
    }
    
    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public String getItem( int position ) {
        return paths.get(position);
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.accessory_list_item, null);
        }
        TextView nameTv = (TextView) convertView.findViewById(R.id.accessory_name);
        nameTv.setText(Utils.getFileNameFromUrl(getItem(position)));
        return convertView;
    }

    
}
