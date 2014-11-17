package net.wicp.yunjigroup.oa.models;

import org.json.JSONObject;

public class Contact extends BaseData{

    private static final long serialVersionUID = 1632657L;
    
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_NAME = "name";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_TEL1 = "tel1";
    public static final String KEY_TEL2 = "tel2";
    public static final String KEY_FENJI = "fenji";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_HOME_NO = "homeNo";
    
    private String department = null;
    private String name = null;
    private String mobile = null;
    private String tel1 = null;
    private String tel2 = null;
    private String fenji = null;
    private String email = null;
    private String homeNo = null;
    public Contact() {
        super();
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment( String department ) {
        this.department = department;
    }
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile( String mobile ) {
        this.mobile = mobile;
    }
    public String getTel1() {
        return tel1;
    }
    public void setTel1( String tel1 ) {
        this.tel1 = tel1;
    }
    public String getTel2() {
        return tel2;
    }
    public void setTel2( String tel2 ) {
        this.tel2 = tel2;
    }
    public String getFenji() {
        return fenji;
    }
    public void setFenji( String fenji ) {
        this.fenji = fenji;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail( String email ) {
        this.email = email;
    }
    public String getHomeNo() {
        return homeNo;
    }
    public void setHomeNo( String homeNo ) {
        this.homeNo = homeNo;
    }
    @Override
    public String toString() {
        return "Contact [department=" + department + ", name=" + name + ", mobile=" + mobile
                + ", tel1=" + tel1 + ", tel2=" + tel2 + ", fenji=" + fenji + ", email=" + email
                + ", homeNo=" + homeNo + "]";
    }
    
    public static Contact fromJson(JSONObject jsonObject){
        if(jsonObject == null)return null;
        Contact contact = null;
        JSONObject dataObject = jsonObject.optJSONObject(KEY_DATA);
        if(dataObject != null){
            contact = new Contact();
            contact.setDepartment(dataObject.optString(KEY_DEPARTMENT));
            contact.setEmail(dataObject.optString(KEY_EMAIL));
            contact.setFenji(dataObject.optString(KEY_FENJI));
            contact.setHomeNo(dataObject.optString(KEY_HOME_NO));
            contact.setMobile(dataObject.optString(KEY_MOBILE));
            contact.setName(dataObject.optString(KEY_NAME));
            contact.setTel1(dataObject.optString(KEY_TEL1));
            contact.setTel2(dataObject.optString(KEY_TEL2));
        }
        return contact;
    }
}
