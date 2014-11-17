package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author chenqiang
 * @2014-10-21
 */
public class NoticeList extends BaseData{

    private static final long serialVersionUID = 146575L;
    
    private List<NoticeSimple> notices = null;

    public List<NoticeSimple> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticeSimple> notices) {
        this.notices = notices;
    }

    @Override
    public String toString() {
        return "NoticeList [notices=" + notices + "]";
    }
    
    /**
     * 从json数据中获取notices
     * @param jsonObject
     * @return 
     */
    public static NoticeList fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        NoticeList list = new NoticeList();
        List<NoticeSimple> simples = new ArrayList<NoticeSimple>();
        list.setErrorcode(jsonObject.optString(KEY_ERRORCODE));
        list.setErrordesc(jsonObject.optString(KEY_ERRORDESC));
        JSONArray array = jsonObject.optJSONArray(KEY_DATA);
        if(array != null){
            int len = array.length();
            for(int i=0;i < len;i++){
                try {
                    JSONObject object = array.getJSONObject(i);
                    NoticeSimple simple = NoticeSimple.fromJson(object);
                    simples.add(simple);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        list.setNotices(simples);
        return list;
    }
}
