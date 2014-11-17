package net.wicp.yunjigroup.oa.models;

import org.json.JSONObject;

public class User extends BaseData{

    private static final long serialVersionUID = 1243577865L;
    
    //服务器返回的字段名
    public static final String KEY_TOKEN = "token";
    public static final String KEY_NAME = "xingming";
    public static final String KEY_DUTY = "gangwei";
    public static final String KEY_DEPARTMENT = "bumen";
    public static final String KEY_YGBH = "ygbh";
    
    private String token = "";
    
    private String name = "";
    
    private String duty = "";
    
    private String department = "";
    
    /**暂时不知道这个参数  */
    private String ygbh = "";

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty( String duty ) {
        this.duty = duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment( String department ) {
        this.department = department;
    }

    public String getYgbh() {
        return ygbh;
    }

    public void setYgbh( String ygbh ) {
        this.ygbh = ygbh;
    }

    @Override
    public String toString() {
        return "User [token=" + token + ", name=" + name + ", duty=" + duty + ", department="
                + department + ", ygbh=" + ygbh + "]";
    }
    
    public static User fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        User user = new User();
        user.setToken(jsonObject.optString(KEY_TOKEN));
        user.setName(jsonObject.optString(KEY_NAME));
        user.setYgbh(jsonObject.optString(KEY_YGBH));
        user.setDuty(jsonObject.optString(KEY_DUTY));
        user.setDepartment(jsonObject.optString(KEY_DEPARTMENT));
        user.setError(jsonObject.optString(KEY_ERROR));
        return user;
    }
    
//    public static User fromCusor(Cursor cursor){
//        
//    }
    

}
