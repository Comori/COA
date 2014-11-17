package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.utils.Contants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MailListAdapter extends BaseAdapter{
    
    private Context mContext = null;
    private List<MailSimple> mailSimples = null;
    private boolean isRecevieBox = true;
    
    public MailListAdapter(Context context,List<MailSimple> mailSimples,boolean isRecevieBox){
        this.mContext = context;
        this.isRecevieBox = isRecevieBox;
        if(mailSimples == null){
            this.mailSimples = new ArrayList<MailSimple>();
        }else{
            this.mailSimples = mailSimples;
        }
    }
    
    @Override
    public int getCount() {
        return mailSimples.size();
    }

    @Override
    public MailSimple getItem( int position ) {
        return mailSimples.get(position);
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mail_list_item, null);
            holder = new ViewHolder();
            holder.markImg = (ImageView) convertView.findViewById(R.id.mail_weidu_img);
            holder.sendUserTv = (TextView) convertView.findViewById(R.id.mail_list_item_senduserTv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.mail_list_item_time);
            holder.titleTv = (TextView) convertView.findViewById(R.id.mail_list_item_titleTv);
            holder.userTypeTv = (TextView) convertView.findViewById(R.id.mail_list_item_userType);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        
        MailSimple mailSimple = getItem(position);
        if(mailSimple.getMarking().equals(Contants.MAIL_UN_READ) && isRecevieBox){
            holder.markImg.setVisibility(View.VISIBLE);
        }else{
            holder.markImg.setVisibility(View.INVISIBLE);
        }
        holder.titleTv.setText(mailSimple.getTitle());
        if(isRecevieBox){
            holder.userTypeTv.setText(mContext.getString(R.string.send_name));
            holder.timeTv.setText(mailSimple.getSendTime());
            holder.sendUserTv.setText(mailSimple.getSendUser());
        }else{
            holder.userTypeTv.setText(mContext.getString(R.string.recevie_name));
            holder.timeTv.setText(mailSimple.getShijian());
            holder.sendUserTv.setText(mailSimple.getShoujianren());
        }
        return convertView;
    }

    static class ViewHolder{
        TextView titleTv;
        TextView sendUserTv;
        TextView timeTv;
        ImageView markImg;
        TextView userTypeTv;
    }
    
    public void updateAll(List<MailSimple> mailSimples){
        if(mailSimples != null){
//            this.mailSimples.clear();
//            this.mailSimples.addAll(mailSimples);
            this.mailSimples = mailSimples;
            notifyDataSetChanged();
        }
    }
}
