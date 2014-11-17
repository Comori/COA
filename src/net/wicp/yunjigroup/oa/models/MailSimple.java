package net.wicp.yunjigroup.oa.models;

import org.json.JSONObject;
/**
 * 
 * @author chenqiang
 * @2014年10月23日
 * @TODO
 */
public class MailSimple extends BaseData{
    private static final long serialVersionUID = 186554L;
    
    public static final String KEY_CARD = "card";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SENDUSER = "senduser";
    public static final String KEY_SHOUJIANREN = "shoujianren"; //服务器返回的数据字段竟然是拼音...
    public static final String KEY_SENDTIME = "sendtime";
    public static final String KEY_SHIJIAN = "shijian";
    public static final String KEY_MARKING = "marking"; //0表示异读，1表示未读
    public static final String KEY_MSG_FLAG = "MsgFlag"; //不知道干什么的
    
    private String card = null;
    private String id = null;
    private String title = null;
    private String sendUser = null;
    private String shoujianren = null;
    private String sendTime = null;
    private String shijian = null;
    private String marking = null;
    private String msgFlag = null;
    public MailSimple() {
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
    public String getTitle() {
        return title;
    }
    public void setTitle( String title ) {
        this.title = title;
    }
    public String getSendUser() {
        return sendUser;
    }
    public void setSendUser( String sendUser ) {
        this.sendUser = sendUser;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime( String sendTime ) {
        this.sendTime = sendTime;
    }
    public String getMarking() {
        return marking;
    }
    public void setMarking( String marking ) {
        this.marking = marking;
    }
    
    public String getShoujianren() {
        return shoujianren;
    }
    public void setShoujianren( String shoujianren ) {
        this.shoujianren = shoujianren;
    }
    public String getShijian() {
        return shijian;
    }
    public void setShijian( String shijian ) {
        this.shijian = shijian;
    }
    public String getMsgFlag() {
        return msgFlag;
    }
    public void setMsgFlag( String msgFlag ) {
        this.msgFlag = msgFlag;
    }
    
    
    @Override
    public String toString() {
        return "MailSimple [card=" + card + ", id=" + id + ", title=" + title + ", sendUser="
                + sendUser + ", shoujianren=" + shoujianren + ", sendTime=" + sendTime
                + ", shijian=" + shijian + ", marking=" + marking + ", msgFlag=" + msgFlag + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((card == null) ? 0 : card.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((marking == null) ? 0 : marking.hashCode());
        result = prime * result + ((msgFlag == null) ? 0 : msgFlag.hashCode());
        result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
        result = prime * result + ((sendUser == null) ? 0 : sendUser.hashCode());
        result = prime * result + ((shijian == null) ? 0 : shijian.hashCode());
        result = prime * result + ((shoujianren == null) ? 0 : shoujianren.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    @Override
    public boolean equals( Object obj ) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MailSimple other = (MailSimple) obj;
        if (card == null) {
            if (other.card != null)
                return false;
        } else if (!card.equals(other.card))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (marking == null) {
            if (other.marking != null)
                return false;
        } else if (!marking.equals(other.marking))
            return false;
        if (msgFlag == null) {
            if (other.msgFlag != null)
                return false;
        } else if (!msgFlag.equals(other.msgFlag))
            return false;
        if (sendTime == null) {
            if (other.sendTime != null)
                return false;
        } else if (!sendTime.equals(other.sendTime))
            return false;
        if (sendUser == null) {
            if (other.sendUser != null)
                return false;
        } else if (!sendUser.equals(other.sendUser))
            return false;
        if (shijian == null) {
            if (other.shijian != null)
                return false;
        } else if (!shijian.equals(other.shijian))
            return false;
        if (shoujianren == null) {
            if (other.shoujianren != null)
                return false;
        } else if (!shoujianren.equals(other.shoujianren))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
    
    
    public static MailSimple fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        MailSimple simple = new MailSimple();
        simple.setCard(jsonObject.optString(KEY_CARD));
        simple.setId(jsonObject.optString(KEY_ID));
        simple.setTitle(jsonObject.optString(KEY_TITLE));
        simple.setSendUser(jsonObject.optString(KEY_SENDUSER));
        simple.setSendTime(jsonObject.optString(KEY_SENDTIME));
        simple.setMarking(jsonObject.optString(KEY_MARKING));
        simple.setShoujianren(jsonObject.optString(KEY_SHOUJIANREN));
        simple.setShijian(jsonObject.optString(KEY_SHIJIAN));
        simple.setMsgFlag(jsonObject.optString(KEY_MSG_FLAG));
        return simple;
    }

    
}
