package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddressGroup extends BaseData{

    private static final long serialVersionUID = 16679899L;
    
    public static final String KEY_COMPANY = "company";
    public static final String KEY_COUNT = "count";
    
    private String groupName;
    
    private String count;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName( String groupName ) {
        this.groupName = groupName;
    }

    public String getCount() {
        return count;
    }

    public void setCount( String count ) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AddressGroup [groupName=" + groupName + ", count=" + count + "]";
    }
    
    public static AddressGroup fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        AddressGroup group = new AddressGroup();
        group.setCount(jsonObject.optString(KEY_COUNT));
        group.setGroupName(jsonObject.optString(KEY_COMPANY));
        return group;
    }
    
    public static List<AddressGroup> fromJsons(JSONObject jsonObject){
        if(jsonObject == null) return null;
        List<AddressGroup> addressGroups = null;
        try {
            JSONArray array = jsonObject.getJSONArray(KEY_DATA);
            if(array != null){
                addressGroups = new ArrayList<AddressGroup>();
                int len = array.length();
                for(int i=0;i < len;i++){
                    JSONObject object = array.getJSONObject(i);
                    AddressGroup group = AddressGroup.fromJson(object);
                    addressGroups.add(group);
                }
                
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return addressGroups;
    }
    
    public static AddressGroup fromCursor(){
        return null;
    }

}
