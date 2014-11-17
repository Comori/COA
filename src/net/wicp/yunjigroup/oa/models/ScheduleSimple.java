package net.wicp.yunjigroup.oa.models;

import org.json.JSONObject;

public class ScheduleSimple extends BaseData{

    private static final long serialVersionUID = 1876353L;
    
    public static final String KEY_CARD = "card";
    public static final String KEY_ID = "id";
    public static final String KEY_SENDTIME = "sendtime";
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    
    private String card = null;
    private String id = null;
    private String sendTime = null;
    private String url = null;
    private String title = null;
    public ScheduleSimple() {
        super();
    }
    public String getCard() {
        return card;
    }
    public void setCard( String card ) {
        this.card = card;
    }
    public String getId() {
        return id;
    }
    public void setId( String id ) {
        this.id = id;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime( String sendTime ) {
        this.sendTime = sendTime;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl( String url ) {
        this.url = url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle( String title ) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "ScheduleSimple [card=" + card + ", id=" + id + ", sendTime=" + sendTime + ", url="
                + url + ", title=" + title + "]";
    }
    
    public static ScheduleSimple fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        ScheduleSimple simple = new ScheduleSimple();
        simple.setCard(jsonObject.optString(KEY_CARD));
        simple.setId(jsonObject.optString(KEY_ID));
        simple.setSendTime(jsonObject.optString(KEY_SENDTIME));
        simple.setTitle(jsonObject.optString(KEY_TITLE));
        simple.setUrl(jsonObject.optString(KEY_URL));
        return simple;
    }
    
}
