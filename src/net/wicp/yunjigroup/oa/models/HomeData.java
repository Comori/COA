package net.wicp.yunjigroup.oa.models;

import org.json.JSONObject;

public class HomeData extends BaseData{
    
    /*{
        "message_count": "858",
        "notice_count": 0,
        "workflow_count": "562",
        "meeting_count": "0",
        "address_count": "1035",
        "error": "\u53c2\u6570\u4f20\u9012\u9519\u8bef"
      }*/

  //服务器返回的字段名
    public static final String KEY_MESSAGE_COUNT = "message_count";
    public static final String KEY_NOTICE_COUNT = "notice_count";
    public static final String KEY_WORKFLOW_COUNT = "workflow_count";
    public static final String KEY_MEETING_COUNT = "meeting_count";
    public static final String KEY_ADDRESS_COUNT = "address_count";
    
    private static final long serialVersionUID = 1456457L;
    
    private int messageCount = 0;
    
    private int noticeCount = 0;
    
    private int workflowCount = 0;
    
    private int meetingCount = 0;
    
    private int addressCount = 0;

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount( int messageCount ) {
        this.messageCount = messageCount;
    }

    public int getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount( int noticeCount ) {
        this.noticeCount = noticeCount;
    }

    public int getWorkflowCount() {
        return workflowCount;
    }

    public void setWorkflowCount( int workflowCount ) {
        this.workflowCount = workflowCount;
    }

    public int getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount( int meetingCount ) {
        this.meetingCount = meetingCount;
    }

    public int getAddressCount() {
        return addressCount;
    }

    public void setAddressCount( int addressCount ) {
        this.addressCount = addressCount;
    }

    @Override
    public String toString() {
        return "HomeData [messageCount=" + messageCount + ", noticeCount=" + noticeCount
                + ", workflowCount=" + workflowCount + ", meetingCount=" + meetingCount
                + ", addressCount=" + addressCount + "]";
    }
    
    public static HomeData fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        HomeData homeData = new HomeData();
        homeData.setAddressCount(jsonObject.optInt(KEY_ADDRESS_COUNT));
        homeData.setError(jsonObject.optString(KEY_ERROR));
        homeData.setMeetingCount(jsonObject.optInt(KEY_MEETING_COUNT));
        homeData.setNoticeCount(jsonObject.optInt(KEY_NOTICE_COUNT));
        homeData.setWorkflowCount(jsonObject.optInt(KEY_WORKFLOW_COUNT));
        homeData.setMessageCount(jsonObject.optInt(KEY_MESSAGE_COUNT));
        return homeData;
    }
    
}
