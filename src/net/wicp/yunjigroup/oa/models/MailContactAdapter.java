package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.ui.SendMailActivity;
import net.wicp.yunjigroup.oa.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MailContactAdapter extends BaseAdapter{
	private Context mContext;
	private List<Cate> parentCates = null;
	private Cate parentCate = null;
	
	public MailContactAdapter(Context context,List<Cate> cates,Cate parentCate){
		this.mContext = context;
		if(cates == null){
			this.parentCates = new ArrayList<Cate>();
		}else{
			this.parentCates = cates;
		}
		if(parentCate == null){
			this.parentCate = new Cate();
		}else{
			this.parentCate = parentCate;
		}
	}

	@Override
	public int getCount() {
		if(parentCate != null && parentCate.getNames() != null){
			return parentCate.getNames().size() + parentCates.size();
		}
		return parentCates.size();
	}

	@Override
	public Object getItem(int position) {
		List<CateName> cateNames = parentCate.getNames();
		if(cateNames == null || cateNames.isEmpty()){
			return parentCates.get(position);
		}else{
			if(position <= cateNames.size()-1) return cateNames.get(position);
			else return parentCates.get(position-cateNames.size());
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		ViewHolder holder = null;
		if(v == null){
			v = LayoutInflater.from(mContext).inflate(R.layout.mail_contact_item, null);
			holder = new ViewHolder();
			holder.selectBtn = (CheckBox) v.findViewById(R.id.mail_contact_select);
			holder.nameText = (TextView) v.findViewById(R.id.mail_contact_name);
			holder.goImg = (ImageView) v.findViewById(R.id.mail_contact_go);
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		Object object = getItem(position);
		if(object instanceof Cate){
			final Cate cate = (Cate) object;
			if( (cate.getCateChiildren() != null && !cate.getCateChiildren().isEmpty()) || (cate.getNames() != null && !cate.getNames().isEmpty())){
				holder.goImg.setVisibility(View.VISIBLE);
			}else{
				holder.goImg.setVisibility(View.GONE);
			}
			holder.nameText.setText(cate.getCateName());
			holder.selectBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton btn, boolean checked) {
					cate.setSelected(checked);
					if(checked){
						Utils.addRecevier("{"+cate.getCateName()+"}");
					}else{
						Utils.removeRecevier("{"+cate.getCateName()+"}");
					}
				}
				
			});
			holder.selectBtn.setChecked(cate.isSelected());
		}else if(object instanceof CateName){
			final CateName cateName = (CateName) object;
			holder.goImg.setVisibility(View.GONE);
			holder.nameText.setText(cateName.getName());
			holder.selectBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton btn, boolean checked) {
					cateName.setSelected(checked);
					if(checked){
						Utils.addRecevier(cateName.getName());
					}else{
						Utils.removeRecevier(cateName.getName());
					}
				}
			});
			holder.selectBtn.setChecked(cateName.isSelected());
		}
		return v;
	}

	static class  ViewHolder{
		CheckBox selectBtn;
		TextView nameText;
		ImageView goImg;
	}
	
	public void update(List<Cate> cates,Cate parentCate) {
		if(parentCate != null){
			this.parentCate = parentCate;
		}else{
			this.parentCate = new Cate();
		}
		this.parentCates = cates;
		notifyDataSetChanged();
	}
}
