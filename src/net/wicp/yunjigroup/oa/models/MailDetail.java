package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MailDetail extends BaseData{

	private static final long serialVersionUID = 1365478L;
	
	public static final String KEY_CARD = "card";
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_SENDUSER = "senduser";
	public static final String KEY_RECEVIEUSER = "receiveuser";
	public static final String KEY_SENDTIME = "sendtime";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_PATH = "path";
	public static final String KEY_ATTACH = "attach";
	
	private String card = null;
	private String id = null;
	private String title = null;
	private String sendUser = null;
	private String receiveUser = null;
	private String sendTime = null;
	private String content = null;
	private transient List<String> accessoryPaths = null;
	public MailDetail() {
		super();
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	
	public String getReceiveUser() {
        return receiveUser;
    }
    public void setReceiveUser( String receiveUser ) {
        this.receiveUser = receiveUser;
    }
    public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getAccessoryPaths() {
		return accessoryPaths;
	}
	public void setAccessoryPaths(List<String> accessoryPaths) {
		this.accessoryPaths = accessoryPaths;
	}
	
	@Override
    public String toString() {
        return "MailDetail [card=" + card + ", id=" + id + ", title=" + title + ", sendUser="
                + sendUser + ", receiveUser=" + receiveUser + ", sendTime=" + sendTime
                + ", content=" + content + ", accessoryPaths=" + accessoryPaths + "]";
    }
    public static MailDetail fromJson(JSONObject jsonObject){
		if(jsonObject == null) return null;
		MailDetail mailDetail = new MailDetail();
		List<String> paths = new ArrayList<String>();
		JSONObject dataObject = jsonObject.optJSONObject(KEY_DATA);
		if(dataObject != null){
			mailDetail.setCard(dataObject.optString(KEY_CARD));
			mailDetail.setContent(dataObject.optString(KEY_CONTENT));
			mailDetail.setId(dataObject.optString(KEY_ID));
			mailDetail.setSendTime(dataObject.optString(KEY_SENDTIME));
			mailDetail.setReceiveUser(dataObject.optString(KEY_RECEVIEUSER));
			mailDetail.setSendUser(dataObject.optString(KEY_SENDUSER));
			mailDetail.setTitle(dataObject.optString(KEY_TITLE));
    		JSONArray array = dataObject.optJSONArray(KEY_ATTACH);
    		if(array != null){
    			int len = array.length();
    			for(int i=0;i < len;i++){
    				JSONObject object = array.optJSONObject(i);
    				paths.add(object.optString(KEY_PATH));
    			}
    		}
		}
		mailDetail.setAccessoryPaths(paths);
		return mailDetail;
	}

}
