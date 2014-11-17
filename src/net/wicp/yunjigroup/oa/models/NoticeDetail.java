package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NoticeDetail extends NoticeSimple {
    private static final long  serialVersionUID = 165475678L;

    public static final String KEY_CONTENT      = "content";
    public static final String KEY_ATTACH = "attach";
    public static final String KEY_PATH = "path";
    
    private String             content          = null;
    private List<String> accessoryPaths = null;

    public NoticeDetail() {
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }
    
    public List<String> getAccessoryPaths() {
        return accessoryPaths;
    }

    public void setAccessoryPaths( List<String> accessoryPaths ) {
        this.accessoryPaths = accessoryPaths;
    }

    @Override
    public String toString() {
        return "NoticeDetail [content=" + content + "]";
    }

    public static NoticeDetail fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        NoticeDetail detail = new NoticeDetail();
        detail.setErrorcode(jsonObject.optString(KEY_ERRORCODE));
        detail.setErrordesc(jsonObject.optString(KEY_ERRORDESC));
        JSONObject object = jsonObject.optJSONObject(KEY_DATA);
        if(object != null){
            detail.setCard(object.optString(KEY_CARD));
            detail.setDepartment(object.optString(KEY_DEPARTMENT));
            detail.setId(object.optString(KEY_ID));
            detail.setSendTime(object.optString(KEY_SEND_TIME));
            detail.setTitle(object.optString(KEY_TITLE));
            detail.setContent(object.optString(KEY_CONTENT));
        }
        JSONArray array = jsonObject.optJSONArray(KEY_ATTACH);
        if(array != null){
            List<String> paths = new ArrayList<String>();
            int len = array.length();
            for(int i=0;i < len;i++){
                try {
                    String path = array.getJSONObject(i).optString(KEY_PATH);
                    paths.add(path);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            detail.setAccessoryPaths(paths);
        }
        return detail;
    }
    
    /**
     * ÊÇ·ñÓÐ¸½¼þ
     * @return
     */
    public boolean hasAccessory(){
        if(accessoryPaths != null && accessoryPaths.size() > 0) return true;
        else return false;
    }
}
