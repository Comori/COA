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
/**
 * 
 * @author chenqiang
 * @2014年10月22日
 * @TODO
 */
public class NoticeListAdapter extends BaseAdapter{
    private Context mContext = null;
    private List<NoticeSimple> noticeSimples = null;
    
    public NoticeListAdapter(Context context,List<NoticeSimple> noticeSimples){
        this.mContext = context;
        if(noticeSimples == null){
            this.noticeSimples = new ArrayList<NoticeSimple>();
        }else{
            this.noticeSimples = noticeSimples;
        }
    }

    @Override
    public int getCount() {
        return noticeSimples.size();
    }

    @Override
    public NoticeSimple getItem( int position ) {
        return noticeSimples.get(position);
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.notice_list_item, null);
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.notice_list_item_title);
            holder.departmentTv = (TextView) convertView.findViewById(R.id.notice_list_item_department);
            holder.timeTv = (TextView) convertView.findViewById(R.id.notice_list_item_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        NoticeSimple simple = getItem(position);
        holder.titleTv.setText(simple.getTitle());
        holder.departmentTv.setText(simple.getDepartment());
        holder.timeTv.setText(simple.getSendTime());
        return convertView;
    }

    static class ViewHolder{
        TextView titleTv;
        TextView departmentTv;
        TextView timeTv;
    }

    public void update( List<NoticeSimple> noticeSimples) {
        if(noticeSimples != null){
            this.noticeSimples = noticeSimples;
            notifyDataSetChanged();
        }
    }
}
