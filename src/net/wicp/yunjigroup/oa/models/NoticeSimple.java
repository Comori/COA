package net.wicp.yunjigroup.oa.models;

import org.json.JSONObject;

public class NoticeSimple extends BaseData{

	private static final long serialVersionUID = 1647783L;
	
	public static final String KEY_CARD = "card";
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_SEND_TIME = "sendtime";
	public static final String KEY_DEPARTMENT = "department";
	
	protected String card = null;
	protected String id = null;
	protected String title = null;
	protected String sendTime = null;
	protected String department = null;
	
	public NoticeSimple() {
		super();
	}
	
	public NoticeSimple(String card, String id, String title, String sendTime,
			String department) {
		super();
		this.card = card;
		this.id = id;
		this.title = title;
		this.sendTime = sendTime;
		this.department = department;
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

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "NoticeSimple [card=" + card + ", id=" + id + ", title=" + title
				+ ", sendTime=" + sendTime + ", department=" + department + "]";
	}
	
	public static NoticeSimple fromJson(JSONObject jsonObject){
		if(jsonObject == null) return null;
		NoticeSimple simple = new NoticeSimple();
		simple.setCard(jsonObject.optString(KEY_CARD));
		simple.setDepartment(jsonObject.optString(KEY_DEPARTMENT));
		simple.setId(jsonObject.optString(KEY_ID));
		simple.setSendTime(jsonObject.optString(KEY_SEND_TIME));
		simple.setTitle(jsonObject.optString(KEY_TITLE));
		return simple;
	}

}
