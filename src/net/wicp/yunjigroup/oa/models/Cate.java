package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Cate extends BaseData{

	private static final long serialVersionUID = 1584656L;
	
	public static final String KEY_CATE_ID = "cate_Id";
	public static final String KEY_PARENT_ID = "cate_ParentId";
	public static final String KEY_CATE_NAME = "cate_Name";
	public static final String KEY_XING_MING = "xingming";
	
	
	private String cateId = null;
	
	private String cateName = null;
	
	private List<CateName> names = null;
	
	private List<Cate> cateChiildren = null;
	
	private boolean isSelected = false;

	public Cate() {
		super();
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public List<CateName> getNames() {
		return names;
	}

	public void setNames(List<CateName> names) {
		this.names = names;
	}

	public List<Cate> getCateChiildren() {
		return cateChiildren;
	}

	public void setCateChiildren(List<Cate> cateChiildren) {
		this.cateChiildren = cateChiildren;
	}

	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "Cate [cateId=" + cateId + ", cateName=" + cateName + ", names="
				+ names + ", cateChiildren=" + cateChiildren + "]";
	}

	public static Cate fromJson(JSONObject jsonObject){
		if(jsonObject == null) return null;
		Cate cate = new Cate();
		List<CateName> names = new ArrayList<CateName>();
		List<Cate> cateChildren = new ArrayList<Cate>();
		cate.setCateId(jsonObject.optString(KEY_CATE_ID));
		cate.setCateName(jsonObject.optString(KEY_CATE_NAME));
		JSONArray nameArray = jsonObject.optJSONArray(KEY_XING_MING);
		if(nameArray != null){
			int len = nameArray.length();
			for(int i=0;i < len;i++){
				CateName cateName = new CateName();
				cateName.setName(nameArray.optString(i));
				names.add(cateName);
			}
		}
		cate.setNames(names);
		JSONArray cateArray = jsonObject.optJSONArray(KEY_PARENT_ID);
		if(cateArray != null){
			int len1 = cateArray.length();
			for(int i1=0;i1 < len1;i1++){
				JSONObject object1 = cateArray.optJSONObject(i1);
				if(object1 != null)cateChildren.add(Cate.fromJson(object1));
			}
		}
		cate.setCateChiildren(cateChildren);
		return cate;
	}
	
}
