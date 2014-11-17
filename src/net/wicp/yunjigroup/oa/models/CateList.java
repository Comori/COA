package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CateList extends BaseData{

	private static final long serialVersionUID = 16758654L;
	
	private List<Cate> parentCates = null;

	public CateList() {
		super();
	}

	public List<Cate> getParentCates() {
		return parentCates;
	}

	public void setParentCates(List<Cate> parentCates) {
		this.parentCates = parentCates;
	}

	@Override
	public String toString() {
		return "CateList [parentCates=" + parentCates + "]";
	}
	
	public static CateList fromJson(JSONObject jsonObject){
		if(jsonObject == null)return null;
		CateList cateList = new CateList();
		List<Cate> cates = new ArrayList<Cate>();
		cateList.setErrordesc(jsonObject.optString(KEY_ERRORDESC));
		cateList.setErrorcode(jsonObject.optString(KEY_ERRORCODE));
		JSONArray dataArray = jsonObject.optJSONArray(KEY_DATA);
		if(dataArray != null){
			int len = dataArray.length();
			for(int i=0;i < len;i++){
				JSONObject object = dataArray.optJSONObject(i);
				if(object != null) {
					Cate cate = Cate.fromJson(object);
					cates.add(cate);
				}
			}
		}
		cateList.setParentCates(cates);
		return cateList;
	}

}
