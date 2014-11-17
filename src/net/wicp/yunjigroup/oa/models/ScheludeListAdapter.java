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

public class ScheludeListAdapter extends BaseAdapter{
    private Context mContext = null;
    private List<ScheduleSimple> simples = null;
    
    public ScheludeListAdapter(Context context,List<ScheduleSimple> simples){
        this.mContext = context;
        if(simples == null){
            this.simples = new ArrayList<ScheduleSimple>();
        }else{
            this.simples = simples;
        }
    }

    @Override
    public int getCount() {
        return simples.size();
    }

    @Override
    public ScheduleSimple getItem( int position ) {
        return simples.get(position);
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View v, ViewGroup arg2 ) {
        ViewHolder holder = null;
        if(v == null){
            v = LayoutInflater.from(mContext).inflate(R.layout.notice_list_item, null);
            holder = new ViewHolder();
            holder.timeTv = (TextView) v.findViewById(R.id.notice_list_item_time);
            holder.titleTv = (TextView) v.findViewById(R.id.notice_list_item_title);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        ScheduleSimple simple = getItem(position);
        holder.timeTv.setText(simple.getSendTime());
        holder.titleTv.setText(simple.getTitle());
        return v;
    }

    static class ViewHolder{
        TextView titleTv;
        TextView timeTv;
    }
    
    public void updateData(List<ScheduleSimple> simples){
        this.simples = simples;
        notifyDataSetChanged();
    }
}
