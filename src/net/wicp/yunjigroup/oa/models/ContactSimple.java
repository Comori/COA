package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactSimple extends BaseData{

	private static final long serialVersionUID = 16346657L;
	
	public static final String KEY_ID = "id";
	public static final String KEY_XINGMING = "xingming";
	
	private String id = null;
	private String name = null;
	public ContactSimple() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ContactSimple [id=" + id + ", name=" + name + "]";
	}
	
	public static ContactSimple fromJson(JSONObject jsonObject){
		if(jsonObject == null) return null;
		ContactSimple simple = new ContactSimple();
		simple.setId(jsonObject.optString(KEY_ID));
		simple.setName(jsonObject.optString(KEY_XINGMING));
		return simple;
	}

	public static List<ContactSimple> fromJsons(JSONObject jsonObject){
        if(jsonObject == null) return null;
        List<ContactSimple> simples = null;
        try {
            JSONArray array = jsonObject.getJSONArray(KEY_DATA);
            if(array != null){
                simples = new ArrayList<ContactSimple>();
                int len = array.length();
                for(int i=0;i < len;i++){
                    JSONObject object = array.getJSONObject(i);
                    ContactSimple simple = ContactSimple.fromJson(object);
                    simples.add(simple);
                }
                
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return simples;
    }
}
